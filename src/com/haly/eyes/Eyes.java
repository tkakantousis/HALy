package com.haly.eyes;

import Luxand.FSDK;
import Luxand.FSDKCam;
import com.haly.brain.Brain;
import com.haly.brain.BrainCommand;
import com.haly.brain.BrainEvent;
import com.haly.brain.Subject;
import com.haly.brain.User;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;


public class Eyes extends TimerTask
{
    private final String FACESDK_ACTIVATION_CODE = "iZfjIuhNIYdnYpAvk+PDpzmU5FEkL2sdKz0KXPWmPyiSEsq8CAjj4ZFquO9CYNgXu/zefjyy1CcbKQ24Tb8FqLOdwzVGS+XDG+Y4s14Ecs5k9akN8AG6YuOJQVH7B4z+kyO07DYCIrqip0sXiVbj973TtqRlPFeDLtiGyF3FhXM=";
    private final int BIOMETRIC_SAMPLES = 10;
    private final int FILTER_FAULTS = 50;
    private Brain brain;
    private FSDKCam.HCamera camera;
    private List<User> users;
    private List<FSDK.FSDK_FaceTemplate.ByReference> biometrics;
    private int ID;
    private int filter;
    private User lastSeen;
    private EyeState state;

    public Eyes(Brain brain) {
        this.brain = brain;

        try {
            int status = FSDK.ActivateLibrary(FACESDK_ACTIVATION_CODE);
            if (status != FSDK.FSDKE_OK) {
                System.err.println("[EYES] Please activate FaceSDK library!");
            }
        }
        catch (java.lang.UnsatisfiedLinkError e) {
            System.err.println("[EYES] Error linking to FaceSDK library!");
        }

        FSDK.Initialize();
        FSDK.SetFaceDetectionParameters(true, false, 100);
        FSDK.SetFaceDetectionThreshold(3);
        FSDKCam.InitializeCapturing();

        FSDKCam.TCameras cameras = new FSDKCam.TCameras();
        int count[] = new int[1];
        FSDKCam.GetCameraList(cameras, count);
        if (count[0] == 0) {
            System.err.println("[EYES] Oh my god, I'm blind!");
        }

        String cameraName = cameras.cameras[0];

        FSDKCam.FSDK_VideoFormats formats = new FSDKCam.FSDK_VideoFormats();
        FSDKCam.GetVideoFormatList(cameraName, formats, count);
        FSDKCam.SetVideoFormat(cameraName, formats.formats[3]);

        camera = new FSDKCam.HCamera();
        int r = FSDKCam.OpenVideoCamera(cameraName, camera);
        if (r != FSDK.FSDKE_OK) {
            System.err.println("[EYES] Error opening eyes!");
        }

        ID = 0;
        filter = 0;
        biometrics = new ArrayList<>();
        users = new ArrayList<>();
        state = EyeState.RECOGNIZING;
    }

    @Override
    public void run() {
        // TODO Add the event sending function
        FSDK.HImage frame = new FSDK.HImage();
        if (FSDKCam.GrabFrame(camera, frame) == FSDK.FSDKE_OK) {
            FSDK.TFacePosition.ByReference facePosition = new FSDK.TFacePosition.ByReference();
            if (FSDK.DetectFace(frame, facePosition) == FSDK.FSDKE_OK) {
                FSDK.FSDK_FaceTemplate.ByReference currentBiometrics = new FSDK.FSDK_FaceTemplate.ByReference();
                if (FSDK.GetFaceTemplateInRegion(frame, facePosition, currentBiometrics) == FSDK.FSDKE_OK) {
                    switch (state) {
                        case RECOGNIZING:
                            boolean match = false;
                            for (User user : users) {
                                Iterator it = user.getBiometrics().iterator();
                                while (!match && it.hasNext()) {
                                    float similarity[] = new float[]{0.0f};
                                    FSDK.FSDK_FaceTemplate.ByReference userBiometric = (FSDK.FSDK_FaceTemplate.ByReference) it.next();
                                    FSDK.MatchFaces(userBiometric, currentBiometrics, similarity);
                                    float threshold[] = new float[]{0.0f};
                                    FSDK.GetMatchingThresholdAtFAR(0.01f, threshold);
                                    if (similarity[0] > threshold[0]) {
                                        match = true;
                                        filter = 0;
                                        if(user.compareTo(lastSeen) != 0) {
                                            brain.processEvent(new BrainEvent(BrainCommand.SEE, Subject.USER, user));
                                            lastSeen = new User(user.getID(), user.getName(), null);
                                        }
                                        break;
                                    }
                                }
                            }
                            
                            filter++;
                            if (!match && filter > FILTER_FAULTS) {
                                filter = 0;
                                state = EyeState.RECORDING;
                            }
                            break;
                        case RECORDING:
                            biometrics.add(currentBiometrics);
                            if (biometrics.size() > BIOMETRIC_SAMPLES) {
                                ArrayList<FSDK.FSDK_FaceTemplate.ByReference> newBiometrics = new ArrayList<>(biometrics);
                                users.add(new User(ID, "Jane-" + ID++, newBiometrics));
                                biometrics.clear();
                                state = EyeState.RECOGNIZING;
                            }
                            break;
                        default:
                            System.err.println("[EYES] If you are here...Fy fan!!");
                            break;
                    }
                }
            }
            else {
                if(lastSeen != null) {
                    brain.processEvent(new BrainEvent(BrainCommand.NOT_SEE, Subject.USER));
                    lastSeen = null;
                }
            }
            FSDK.FreeImage(frame);
        }
    }
}
