package ninja.lucas.tabhosttest;

/**
 * Created by Lucas on 9/22/15.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class TabBar extends HorizontalScrollView {

    private OnTabClicked mOnTabClicked;
    private LinearLayout mLayout;

    public TabBar(Context context) {
        super(context);
        initView(context, null);
    }

    public TabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public TabBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    protected void initView(Context context, AttributeSet attrs) {
        mLayout = new LinearLayout(context);
        addView(mLayout, new LayoutParams(WRAP_CONTENT, MATCH_PARENT));
    }

    public void setOnTabClicked(OnTabClicked onTabClicked) {
        mOnTabClicked = onTabClicked;
    }

    public void addTab(CharSequence text) {
        mLayout.addView(createTabView(text));
    }

    public void clearTabs() {
        mLayout.removeAllViews();
    }

    private View createTabView(CharSequence text) {
        TextView tab = (TextView)LayoutInflater.from(getContext())
                .inflate(R.layout.tab_view, mLayout, false);
        tab.setOnClickListener(mTabClickListener);
        tab.setText(text);
        return tab;
    }

    private View.OnClickListener mTabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i = mLayout.getChildCount() - 1; i >= 0; i--) {
                mLayout.getChildAt(i).setSelected(false);
            }
            int pos = mLayout.indexOfChild(v);
            mLayout.getChildAt(pos).setSelected(true);
            if(mOnTabClicked != null) {
                mOnTabClicked.onTabClicked(v, pos);
            }
        }
    };

    public static interface OnTabClicked {
        public void onTabClicked(View tab, int pos);
    }

}
