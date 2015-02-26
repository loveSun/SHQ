package net.yasite.util;

import java.util.Timer;
import java.util.TimerTask;

import net.yasite.test.R;


import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;

public class SlidingSwitcherView extends RelativeLayout implements OnTouchListener {

	/**
	 * �ò˵���������ָ������Ҫ�ﵽ���ٶȡ�
	 */
	public static final int SNAP_VELOCITY = 200;

	/**
	 * SlidingSwitcherView�Ŀ�ȡ�
	 */
	private int switcherViewWidth;

	/**
	 * ��ǰ��ʾ��Ԫ�ص��±ꡣ
	 */
	private int currentItemIndex;

	/**
	 * �˵��а��Ԫ������
	 */
	private int itemsCount;

	/**
	 * ����Ԫ�ص�ƫ�Ʊ߽�ֵ��
	 */
	private int[] borders;

	/**
	 * �����Ի����������Ե��ֵ�ɲ˵��а��Ԫ������������marginLeft�����ֵ֮�󣬲����ټ��١�
	 * 
	 */
	private int leftEdge = 0;

	/**
	 * �����Ի��������ұ�Ե��ֵ��Ϊ0��marginLeft�����ֵ֮�󣬲��������ӡ�
	 */
	private int rightEdge = 0;

	/**
	 * ��¼��ָ����ʱ�ĺ���ꡣ
	 */
	private float xDown;

	/**
	 * ��¼��ָ�ƶ�ʱ�ĺ���ꡣ
	 */
	private float xMove;

	/**
	 * ��¼�ֻ�̧��ʱ�ĺ���ꡣ
	 */
	private float xUp;

	/**
	 * �˵����֡�
	 */
	private LinearLayout itemsLayout;

	/**
	 * ��ǩ���֡�
	 */
	private LinearLayout dotsLayout;

	/**
	 * �˵��еĵ�һ��Ԫ�ء�
	 */
	private View firstItem;

	/**
	 * �˵��е�һ��Ԫ�صĲ��֣����ڸı�leftMargin��ֵ����������ǰ��ʾ����һ��Ԫ�ء�
	 */
	private MarginLayoutParams firstItemParams;

	/**
	 * ���ڼ�����ָ�������ٶȡ�
	 */
	private VelocityTracker mVelocityTracker;

	/**
	 * ��дSlidingSwitcherView�Ĺ��캯������������XML�����õ�ǰ���Զ��岼�֡�
	 * 
	 * @param context
	 * @param attrs
	 */
//	public SlidingSwitcherView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//	}	
	public SlidingSwitcherView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlidingSwitcherView);
		boolean isAutoPlay = a.getBoolean(R.styleable.SlidingSwitcherView_auto_play, false);
		if (isAutoPlay) {
			startAutoPlay();
		}
		a.recycle();
	}

	/**
	 * ��������һ��Ԫ�ء�
	 */
	public void scrollToNext() {
		new ScrollTask().execute(-20);
	}

	/**
	 * ��������һ��Ԫ�ء�
	 */
	public void scrollToPrevious() {
		new ScrollTask().execute(20);
	}

	/**
	 * ��onLayout�������趨�˵�Ԫ�غͱ�ǩԪ�صĲ���
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			initializeItems();
			initializeDots();
		}
	}

	/**
	 * ��ʼ���˵�Ԫ�أ�Ϊÿһ����Ԫ�����Ӽ����¼������Ҹı�������Ԫ�صĿ�ȣ������ǵ��ڸ�Ԫ�صĿ�ȡ�
	 */
	private void initializeItems() {
		switcherViewWidth = getWidth();
		itemsLayout = (LinearLayout) getChildAt(0);
		itemsCount = itemsLayout.getChildCount();
		borders = new int[itemsCount];
		for (int i = 0; i < itemsCount; i++) {
			borders[i] = -i * switcherViewWidth;
			View item = itemsLayout.getChildAt(i);
			MarginLayoutParams params = (MarginLayoutParams) item.getLayoutParams();
			params.width = switcherViewWidth;
			item.setLayoutParams(params);
			item.setOnTouchListener(this);
		}
		leftEdge = borders[itemsCount - 1];
		firstItem = itemsLayout.getChildAt(0);
		firstItemParams = (MarginLayoutParams) firstItem.getLayoutParams();
	}

	/**
	 * ��ʼ����ǩԪ�ء�
	 */
	private void initializeDots() {
		dotsLayout = (LinearLayout) getChildAt(1);
		refreshDotsLayout();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		createVelocityTracker(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// ��ָ����ʱ����¼����ʱ�ĺ����
			xDown = event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			// ��ָ�ƶ�ʱ���ԱȰ���ʱ�ĺ���꣬������ƶ��ľ��룬��������಼�ֵ�leftMarginֵ���Ӷ���ʾ��������಼��
			xMove = event.getRawX();
			int distanceX = (int) (xMove - xDown) - (currentItemIndex * switcherViewWidth);
			firstItemParams.leftMargin = distanceX;
			if (beAbleToScroll()) {
				firstItem.setLayoutParams(firstItemParams);
			}
			break;
		case MotionEvent.ACTION_UP:
			// ��ָ̧��ʱ�������жϵ�ǰ���Ƶ���ͼ���Ӷ�����ǹ�������಼�֣����ǹ������Ҳ಼��
			xUp = event.getRawX();
			if (beAbleToScroll()) {
				if (wantScrollToPrevious()) {
					if (shouldScrollToPrevious()) {
						currentItemIndex--;
						scrollToPrevious();
						refreshDotsLayout();
					} else {
						scrollToNext();
					}
				} else if (wantScrollToNext()) {
					if (shouldScrollToNext()) {
						currentItemIndex++;
						scrollToNext();
						refreshDotsLayout();
					} else {
						scrollToPrevious();
					}
				}
			}
			recycleVelocityTracker();
			break;
		}
		return false;
	}

	/**
	 * ��ǰ�Ƿ��ܹ���������������һ�������һ��Ԫ��ʱ�������ٹ�����
	 * 
	 * @return ��ǰleftMargin��ֵ��leftEdge��rightEdge֮�䷵��true,���򷵻�false��
	 */
	private boolean beAbleToScroll() {
		return firstItemParams.leftMargin < rightEdge && firstItemParams.leftMargin > leftEdge;
	}

	/**
	 * �жϵ�ǰ���Ƶ���ͼ�ǲ������������һ���˵�Ԫ�ء������ָ�ƶ��ľ�������������Ϊ��ǰ��������Ҫ��������һ���˵�Ԫ�ء�
	 * 
	 * @return ��ǰ�������������һ���˵�Ԫ�ط���true�����򷵻�false��
	 */
	private boolean wantScrollToPrevious() {
		return xUp - xDown > 0;
	}

	/**
	 * �жϵ�ǰ���Ƶ���ͼ�ǲ������������һ���˵�Ԫ�ء������ָ�ƶ��ľ����Ǹ�������Ϊ��ǰ��������Ҫ��������һ���˵�Ԫ�ء�
	 * 
	 * @return ��ǰ�������������һ���˵�Ԫ�ط���true�����򷵻�false��
	 */
	private boolean wantScrollToNext() {
		return xUp - xDown < 0;
	}

	/**
	 * �ж��Ƿ�Ӧ�ù�������һ���˵�Ԫ�ء������ָ�ƶ����������Ļ��1/2��������ָ�ƶ��ٶȴ���SNAP_VELOCITY��
	 * ����ΪӦ�ù�������һ���˵�Ԫ�ء�
	 * 
	 * @return ���Ӧ�ù�������һ���˵�Ԫ�ط���true�����򷵻�false��
	 */
	private boolean shouldScrollToNext() {
		return xDown - xUp > switcherViewWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
	}

	/**
	 * �ж��Ƿ�Ӧ�ù�������һ���˵�Ԫ�ء������ָ�ƶ����������Ļ��1/2��������ָ�ƶ��ٶȴ���SNAP_VELOCITY��
	 * ����ΪӦ�ù�������һ���˵�Ԫ�ء�
	 * 
	 * @return ���Ӧ�ù�������һ���˵�Ԫ�ط���true�����򷵻�false��
	 */
	private boolean shouldScrollToPrevious() {
		return xUp - xDown > switcherViewWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
	}

	/**
	 * ˢ�±�ǩԪ�ز��֣�ÿ��currentItemIndexֵ�ı��ʱ��Ӧ�ý���ˢ�¡�
	 */
	private void refreshDotsLayout() {
		dotsLayout.removeAllViews();
		for (int i = 0; i < itemsCount; i++) {
			LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(0,
					LayoutParams.FILL_PARENT);
			linearParams.weight = 1;
			RelativeLayout relativeLayout = new RelativeLayout(getContext());
			ImageView image = new ImageView(getContext());
			if (i == currentItemIndex) {
				image.setBackgroundResource(R.drawable.ic_launcher);
			} else {
				image.setBackgroundResource(R.drawable.ic_launcher);
			}
			RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			relativeParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			relativeLayout.addView(image, relativeParams);
			dotsLayout.addView(relativeLayout, linearParams);
		}
	}

	/**
	 * ����VelocityTracker���󣬲��������¼����뵽VelocityTracker���С�
	 * 
	 * @param event
	 *            �Ҳ಼�ּ���ؼ��Ļ����¼�
	 */
	private void createVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

	/**
	 * ��ȡ��ָ���Ҳ಼�ֵļ���View�ϵĻ����ٶȡ�
	 * 
	 * @return �����ٶȣ���ÿ�����ƶ��˶�������ֵΪ��λ��
	 */
	private int getScrollVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) mVelocityTracker.getXVelocity();
		return Math.abs(velocity);
	}

	/**
	 * ����VelocityTracker����
	 */
	private void recycleVelocityTracker() {
		mVelocityTracker.recycle();
		mVelocityTracker = null;
	}

	/**
	 * ���˵�����ʱ���Ƿ��д�Խborder��border��ֵ���洢��{@link #borders}�С�
	 * 
	 * @param leftMargin
	 *            ��һ��Ԫ�ص���ƫ��ֵ
	 * @param speed
	 *            �������ٶȣ�����˵�����ҹ���������˵�����������
	 * @return ��Խ�κ�һ��border�˷���true�����򷵻�false��
	 */
	private boolean isCrossBorder(int leftMargin, int speed) {
		for (int border : borders) {
			if (speed > 0) {
				if (leftMargin >= border && leftMargin - speed < border) {
					return true;
				}
			} else {
				if (leftMargin <= border && leftMargin - speed > border) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * �ҵ��뵱ǰ��leftMargin����һ��borderֵ��
	 * 
	 * @param leftMargin
	 *            ��һ��Ԫ�ص���ƫ��ֵ
	 * @return �뵱ǰ��leftMargin����һ��borderֵ��
	 */
	private int findClosestBorder(int leftMargin) {
		int absLeftMargin = Math.abs(leftMargin);
		int closestBorder = borders[0];
		int closestMargin = Math.abs(Math.abs(closestBorder) - absLeftMargin);
		for (int border : borders) {
			int margin = Math.abs(Math.abs(border) - absLeftMargin);
			if (margin < closestMargin) {
				closestBorder = border;
				closestMargin = margin;
			}
		}
		return closestBorder;
	}
	class ScrollToFirstItemTask extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... speed) {
			int leftMargin = firstItemParams.leftMargin;
			while (true) {
				leftMargin = leftMargin + speed[0];
				// ��leftMargin����0ʱ��˵���Ѿ��������˵�һ��Ԫ�أ����ѭ��
				if (leftMargin > 0) {
					leftMargin = 0;
					break;
				}
				publishProgress(leftMargin);
				sleep(20);
			}
			return leftMargin;
		}

		@Override
		protected void onProgressUpdate(Integer... leftMargin) {
			firstItemParams.leftMargin = leftMargin[0];
			firstItem.setLayoutParams(firstItemParams);
		}

		@Override
		protected void onPostExecute(Integer leftMargin) {
			firstItemParams.leftMargin = leftMargin;
			firstItem.setLayoutParams(firstItemParams);
		}

	}
	/**
	 * ��������һ��Ԫ�ء�
	 */
	public void scrollToFirstItem() {
		new ScrollToFirstItemTask().execute(20 * itemsCount);
	}
	/**
	 * �����ڶ�ʱ�����в���UI���档
	 */
	private Handler handler = new Handler();

	/**
	 * ����ͼƬ�Զ����Ź��ܣ������������һ��ͼƬ��ʱ�򣬻��Զ��ع�����һ��ͼƬ��
	 */
	public void startAutoPlay() {
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (currentItemIndex == itemsCount - 1) {
					currentItemIndex = 0;
					handler.post(new Runnable() {
						@Override
						public void run() {
							scrollToFirstItem();
							refreshDotsLayout();
						}
					});
				} else {
					currentItemIndex++;
					handler.post(new Runnable() {
						@Override
						public void run() {
							scrollToNext();
							refreshDotsLayout();
						}
					});
				}
			}
		}, 3000, 3000);
	}

	class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... speed) {
			int leftMargin = firstItemParams.leftMargin;
			// ��ݴ�����ٶ����������棬��������Խborderʱ�����ѭ����
			while (true) {
				leftMargin = leftMargin + speed[0];
				if (isCrossBorder(leftMargin, speed[0])) {
					leftMargin = findClosestBorder(leftMargin);
					break;
				}
				publishProgress(leftMargin);
				// Ϊ��Ҫ�й���Ч�����ÿ��ѭ��ʹ�߳�˯��10���룬�������۲��ܹ���������������
				sleep(10);
			}
			return leftMargin;
		}

		@Override
		protected void onProgressUpdate(Integer... leftMargin) {
			firstItemParams.leftMargin = leftMargin[0];
			firstItem.setLayoutParams(firstItemParams);
		}

		@Override
		protected void onPostExecute(Integer leftMargin) {
			firstItemParams.leftMargin = leftMargin;
			firstItem.setLayoutParams(firstItemParams);
		}
	}

	/**
	 * ʹ��ǰ�߳�˯��ָ���ĺ�����
	 * 
	 * @param millis
	 *            ָ����ǰ�߳�˯�߶�ã��Ժ���Ϊ��λ
	 */
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}