#include <jni.h>
#include <opencv2/opencv.hpp>


using namespace cv;


//extern "C"
//JNIEXPORT void JNICALL
//Java_com_tistory_webnautes_useopencvwithcmake_MainActivity_ConvertRGBtoGray(JNIEnv *env,
//                                                                            jobject instance,
//                                                                            jlong matAddrInput,
//                                                                            jlong matAddrResult) {
//
//
//
//    // 입력 RGBA 이미지를 GRAY 이미지로 변환
//    Mat &matInput = *(Mat *)matAddrInput;
//    Mat &matResult = *(Mat *)matAddrResult;
//
//    cvtColor(matInput, matResult, COLOR_RGBA2GRAY);
//
//
//}

extern "C"
JNIEXPORT void JNICALL
Java_com_tistory_webnautes_useopencvwithcmake_MainActivity_loadImage(JNIEnv *env, jobject instance,
                                                                     jstring imageFileName_,
                                                                     jlong img) {
    const char *imageFileName = env->GetStringUTFChars(imageFileName_, 0);

    Mat &img_input = *(Mat *) img;

    img_input = imread(imageFileName, IMREAD_COLOR);


    env->ReleaseStringUTFChars(imageFileName_, imageFileName);
}


extern "C"
JNIEXPORT void JNICALL
Java_com_tistory_webnautes_useopencvwithcmake_MainActivity_imageprocessing(JNIEnv *env,
                                                                           jobject instance,
                                                                           jlong inputImage,
                                                                           jlong outputImage) {

    Mat &img_input = *(Mat *) inputImage;

    Mat &img_output = *(Mat *) outputImage;


    cvtColor( img_input, img_input, COLOR_BGR2RGB);

    cvtColor( img_input, img_output, COLOR_RGB2GRAY);

    blur( img_output, img_output, Size(5,5) );

    Canny( img_output, img_output, 50, 150, 5 );


}