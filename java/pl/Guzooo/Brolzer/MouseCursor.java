package pl.Guzooo.Brolzer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
public class MouseCursor extends AppCompatImageView {

    private float mousesSpeed = 0.01f;

    private OnSelectListener selectListener;
    private OnComeToEdgeListener comeToEdgeListener;

    public interface OnSelectListener {
        void onSelect();
    }

    public interface OnComeToEdgeListener {
        void onComeToEdge();
    }

    public MouseCursor (Context context, AttributeSet attr){
        super(context, attr);
    }

    public void setOnSelectListener(OnSelectListener listener){
        selectListener = listener;
    }

    public void setOnComeToEdgeListener(OnComeToEdgeListener listener){
        comeToEdgeListener = listener;
    }

    public boolean ClickDPad(int keyCode){
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                Up();
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                Right();
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                Down();
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                Left();
                return true;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                Select();
                return true;
        }
        return false;
    }

    private void Up(){
        Move(0, -1);
    }

    private void Right(){
        Move(1, 0);
    }

    private void Down(){
        Move(0, 1);
    }

    private void Left(){
        Move(-1, 0);
    }

    private void Select(){
        if(selectListener != null)
            selectListener.onSelect();
    }

    private void Move(int x, int y){
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) getLayoutParams();
        float newX = getNewX(params.horizontalBias, x);
        float newY = getNewY(params.verticalBias, y);
        params.horizontalBias = newX;
        params.verticalBias = newY;
        setLayoutParams(params);
    }

    private float getNewX(float x, int direction){
        float newX = x + direction * mousesSpeed;
        if(CheckMove(newX))
            return newX;
        //TODO: MOVE_PAGE();
        return x;
    }

    private float getNewY(float y, int direction){
        float newY = y + direction * mousesSpeed;
        if(CheckMove(newY))
            return newY;
        //TODO: MOVE_PAGE();
        return y;
    }

    private boolean CheckMove(float f){
        if(f <= 0.02)
            return false;
        if(f >= 0.98)
            return false;
        Log.d("G LOG", "tu mamy: " + f);
        return true;
    }
}
