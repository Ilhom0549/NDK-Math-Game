#include <jni.h>
#include <string>


extern "C" JNIEXPORT jdouble JNICALL
Java_uz_ilkhomkhuja_ndkmathgame_MainActivity_addition(
        JNIEnv *env,
        jobject /* this */,
        jdouble firstNumber, jdouble secondNumber) {

    return firstNumber + secondNumber;
}

extern "C" JNIEXPORT jdouble JNICALL
Java_uz_ilkhomkhuja_ndkmathgame_MainActivity_subtraction(
        JNIEnv *env,
        jobject /* this */,
        jdouble firstNumber, jdouble secondNumber) {
    return firstNumber - secondNumber;
}

extern "C" JNIEXPORT jdouble JNICALL
Java_uz_ilkhomkhuja_ndkmathgame_MainActivity_multiply(
        JNIEnv *env,
        jobject /* this */,
        jdouble firstNumber, jdouble secondNumber) {
    return firstNumber * secondNumber;
}

extern "C" JNIEXPORT jdouble JNICALL
Java_uz_ilkhomkhuja_ndkmathgame_MainActivity_division(
        JNIEnv *env,
        jobject /* this */,
        jdouble firstNumber, jdouble secondNumber) {
    return firstNumber / secondNumber;
}
