#include <com_example_suman_uniquepausefinal_ViolaJonesAlgorithm_OpencvClass.h>

jint b;
JNIEXPORT jint JNICALL Java_com_example_suman_uniquepausefinal_ViolaJonesAlgorithm_OpencvClass_faceDetection
  (JNIEnv *, jclass, jlong addrRbga)
{

    Mat& frame = *(Mat*)addrRbga;
    jint retVal;
    int conv = detectFace(frame);
    retVal = (jint)conv;
    return retVal;
}

int detectFace(Mat& frame)
{
    int face = 0;
    b=face;


    CascadeClassifier face_cascade;
    CascadeClassifier eyes_cascade;

    const char *faceXML[7] ={"/storage/sdcard0/data/haarcascade_frontalface_alt.xml",
                             "/storage/sdcard1/data/haarcascade_frontalface_alt.xml",
                             "/storage/extSdCard/data/haarcascade_frontalface_alt.xml",
                             "/storage/usbcard1/data/haarcascade_frontalface_alt.xml",
                             "/storage/emulated/0/data/haarcascade_frontalface_alt.xml",
                             "/storage/emulated/1/data/haarcascade_frontalface_alt.xml",
                             "/storage/emulated/legacy/data/haarcascade_frontalface_alt.xml"
    };

    const char *eyeXML[7] = {"/storage/sdcard0/data/haarcascade_eye_tree_eyeglasses.xml",
                             "/storage/sdcard1/data/haarcascade_eye_tree_eyeglasses.xml",
                             "/storage/extSdCard/data/haarcascade_eye_tree_eyeglasses.xml",
                             "/storage/usbcard1/data/haarcascade_eye_tree_eyeglasses.xml",
                             "/storage/emulated/0/data/haarcascade_eye_tree_eyeglasses.xml",
                             "/storage/emulated/1/data/haarcascade_eye_tree_eyeglasses.xml",
                             "/storage/emulated/legacy/data/haarcascade_eye_tree_eyeglasses.xml"
    };
    for(int i=0; i<7; i++){
        String face_cascade_name =faceXML[i];
        String eyes_cascade_name = eyeXML[i];//"/storage/sdcard1/data/haarcascade_eye_tree_eyeglasses.xml";   //For sonu sdcard0

        //-- 1. Load the cascades
        if(!face_cascade.load( face_cascade_name )){
            printf("--(!)Error loading\n");
        } else{
            break;
        }

        if(!eyes_cascade.load( eyes_cascade_name )){
            printf("--(!)Error loading\n");
        } else{
            break;
        }
    }

    std::vector<Rect> faces;
    Mat frame_gray;

    cvtColor( frame, frame_gray, CV_BGR2GRAY );
    equalizeHist( frame_gray, frame_gray );

    //-- Detect faces
    face_cascade.detectMultiScale( frame_gray, faces, 1.1, 2, 0|CV_HAAR_SCALE_IMAGE, Size(30, 30) );
    for (size_t i = 0; i < faces.size(); i++) {
        Point center(faces[i].x + faces[i].width * 0.5, faces[i].y + faces[i].height * 0.5);
        ellipse(frame, center, Size(faces[i].width * 0.5, faces[i].height * 0.5), 0, 0, 360,
                Scalar(255, 0, 255), 4, 8, 0);

        Mat faceROI = frame_gray(faces[i]);
        std::vector <Rect> eyes;

        //-- In each face, detect eyes
        eyes_cascade.detectMultiScale(faceROI, eyes, 1.1, 2, 0 | CV_HAAR_SCALE_IMAGE,
                                      Size(30, 30));

        for (size_t j = 0; j < eyes.size(); j++) {
            Point center(faces[i].x + eyes[j].x + eyes[j].width * 0.5,
                         faces[i].y + eyes[j].y + eyes[j].height * 0.5);
            int radius = cvRound((eyes[j].width + eyes[j].height) * 0.25);
            circle(frame, center, radius, Scalar(255, 0, 0), 4, 8, 0);

        }


        face = 1;
        b=face;
    }
    return face;
}


