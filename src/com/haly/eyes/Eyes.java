package com.haly.eyes;

import Luxand.FSDK;
import Luxand.FSDKCam;
import com.haly.brain.Brain;
import java.util.TimerTask;


public class Eyes extends TimerTask
{
    private final String FACESDK_ACTIVATION_CODE = "iZfjIuhNIYdnYpAvk+PDpzmU5FEkL2sdKz0KXPWmPyiSEsq8CAjj4ZFquO9CYNgXu/zefjyy1CcbKQ24Tb8FqLOdwzVGS+XDG+Y4s14Ecs5k9akN8AG6YuOJQVH7B4z+kyO07DYCIrqip0sXiVbj973TtqRlPFeDLtiGyF3FhXM=";
    private Brain brain;
    private FSDKCam.HCamera camera;

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
            System.err.println("[EYES] Error openiong eyes!");
        }
    }

    @Override
    public void run() {
        FSDK.HImage frame = new FSDK.HImage();
        if (FSDKCam.GrabFrame(camera, frame) == FSDK.FSDKE_OK) {
            FSDK.TFacePosition.ByReference facePosition = new FSDK.TFacePosition.ByReference();
            if (FSDK.DetectFace(frame, facePosition) == FSDK.FSDKE_OK) {
                System.out.println("I can see someone @ (" + facePosition.xc + ", " + facePosition.yc + ")! :)");
            }
            else {
                System.out.println("I can see none! :(");
            }
            FSDK.FreeImage(frame);
        }
    }
}
