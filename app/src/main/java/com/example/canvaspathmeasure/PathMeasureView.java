package com.example.canvaspathmeasure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;

/**
 * @author zhang
 */
public class PathMeasureView extends View {

    private Paint mPaint = new Paint();
    private Paint mLinePaint = new Paint();
    private Bitmap mBitmap;

    public PathMeasureView(Context context) {
        super(context);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(4);

        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStrokeWidth(6);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow, options);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mLinePaint);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mLinePaint);

        canvas.translate(getWidth() / 2, getHeight() / 2);


//        Path path = new Path();
//        path.lineTo(0, 200);
//        path.lineTo(200, 200);
//        path.lineTo(200, 0);
//
//        //pathMeasure需要关联一个创建好的path
//        //forceClosed 是否强制闭合path
//        //path如果有更新，需要重现setPath
//        PathMeasure pathMeasure = new PathMeasure();
//        pathMeasure.setPath(path, true);
//        Log.e("canvas", "onDraw:" + pathMeasure.getLength());
//
//        PathMeasure pathMeasure2 = new PathMeasure();
//        pathMeasure2.setPath(path, false);
//        Log.e("canvas", "onDraw:" + pathMeasure2.getLength());
//
//        PathMeasure pathMeasure1 = new PathMeasure(path, false);
//        Log.e("canvas", "onDraw:" + pathMeasure1.getLength());
//
//        path.lineTo(200, -200);
//        Log.e("canvas", "onDraw:" + pathMeasure1.getLength());
//        pathMeasure1.setPath(path, false);
//        Log.e("canvas", "onDraw:" + pathMeasure1.getLength());

//        Path path = new Path();
//        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
//        Path dst = new Path();
//        dst.lineTo(-300, -300);
//        PathMeasure pathMeasure = new PathMeasure(path, false);
//        //截取一部分存入dst中，并且使用moveTo保持截取得到的Path第一个点位置不变。
//        pathMeasure.getSegment(200, 1000, dst, true);
//
//        canvas.drawPath(path, mPaint);
//        canvas.drawPath(dst, mLinePaint);

        //nextCoutour
//        Path path = new Path();
//        path.addRect(-100, -100, 100, 100, Path.Direction.CW);
//        path.addOval(-200, -200, 200, 200, Path.Direction.CW);
//        canvas.drawPath(path, mPaint);
//        PathMeasure pathMeasure = new PathMeasure(path, false);
//        Log.e("canvas", "onDraw:" + pathMeasure.getLength());
//        //切换到下一条path
//        //pathMeasure.getLength() 获取当前path的长度
//        pathMeasure.nextContour();
//        Log.e("canvas", "onDraw:" + pathMeasure.getLength());

        mPath.reset();

        mPath.addCircle(0, 0, 200, Path.Direction.CW);
        canvas.drawPath(mPath, mPaint);

        mFloat += 0.01;
        if (mFloat >= 1) {
            mFloat = 0;
        }

//        PathMeasure pathMeasure = new PathMeasure(mPath, false);
//        pathMeasure.getPosTan(pathMeasure.getLength() * mFloat, pos, tan);
//        Log.e("canvas", "onDraw:pos[0]=" + pos[0] + ";pos[1]=" + pos[1]);
//        Log.e("canvas", "onDraw:tan[0]=" + tan[0] + ";tan[1]=" + tan[1]);
//        //计算当前切线与x轴的夹角
//        double degree = Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI;
//        Log.e("canvas", "degree:" + degree);
//
//        matrix.reset();
//        //进行角度旋转
//        matrix.postRotate((float) degree, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
//        //将图片的绘制中心与当前点重合
//        matrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);
//        canvas.drawBitmap(mBitmap, matrix, mPaint);

        PathMeasure pathMeasure = new PathMeasure(mPath, false);
        //将pos信息和tan信息保存到matrix中
        pathMeasure.getMatrix(pathMeasure.getLength() * mFloat, matrix,
                PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);
        //将图片的旋转坐标调整到图片中心位置
        matrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, matrix, mPaint);

        invalidate();
    }

    private Matrix matrix = new Matrix();
    private Path mPath = new Path();
    private float[] pos = new float[2];
    private float[] tan = new float[2];
    private float mFloat;
}
