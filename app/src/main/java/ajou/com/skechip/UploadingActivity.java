package ajou.com.skechip;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

public class UploadingActivity extends AppCompatActivity {
    /*
    * TODO: 시간표 업로드 액티비티에서 해야할 것
    *
    * 충희-> 갤러리이미지 불러오고 시간표 이미지 선택한 뒤 시간표 이미지 분석
    *
    * 제호-> 1. 시간표 업로드 끝나면 메인 액티비티의 ep_fragment의 레이아웃 fragment_time_table 로 업데이트 하기
    *       2. 메인 액티비티 SavePreference 해서 timetableUploaded(boolean) true 로 save 해두기
    *
    * 건형-> DB 저장(서버)
    *
    */
    static {
        System.loadLibrary("opencv_java4");
        System.loadLibrary("native-lib");
    }

    ImageView imageVIewInput;
    ImageView imageVIewOuput;
    private Mat img_input;
    private Mat img_output;

    private static final String TAG = "sdk";
    private final int GET_GALLERY_IMAGE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploading);

        imageVIewOuput = (ImageView) findViewById(R.id.imageView2);
        Button sendImageButton = (Button) findViewById(R.id.button3);
        sendImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);

            }
        });
    }
    private String getRealPathFromURI(Uri contentUri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToFirst();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        return cursor.getString(column_index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ( requestCode == GET_GALLERY_IMAGE){

            String imagePath = getRealPathFromURI(data.getData());
            img_input = new Mat();
            img_output = new Mat();

            loadImage(imagePath, img_input.getNativeObjAddr());
            imageprocess_and_showResult();

        }
    }

    private void imageprocess_and_showResult() {

        imageprocessing(img_input.getNativeObjAddr(), img_output.getNativeObjAddr());

        Bitmap bitmapInput = Bitmap.createBitmap(img_input.cols(), img_input.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img_input, bitmapInput);
        //imageVIewInput.setImageBitmap(bitmapInput);

        Bitmap bitmapOutput = Bitmap.createBitmap(img_output.cols(), img_output.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img_output, bitmapOutput);
        //imageVIewOuput.setImageBitmap(bitmapOutput);
    }
    public native void loadImage(String imageFileName, long img);
    public native void imageprocessing(long inputImage, long outputImage);

}
