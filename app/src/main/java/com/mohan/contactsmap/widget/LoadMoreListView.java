package com.mohan.contactsmap.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.mohan.contactsmap.R;

/**
 * This class will help user to make Loadmore ListView
 */
public class LoadMoreListView extends ListView implements OnScrollListener {
    private int visibleThreshold = 3;
    private int previousTotal = 0;
    private int mCurrentScrollState;

    private OnScrollListener mOnScrollListener;
    private LayoutInflater mInflater;

    private RelativeLayout mFooterView;
    private ProgressBar mProgressView;

    private EndlessScrollListener.OnLoadMoreListener mOnLoadMoreListener;

    private boolean mLoading = false;

    public LoadMoreListView(Context context) {
        super(context);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Footer view
        mFooterView = (RelativeLayout) mInflater.inflate(
                R.layout.view_loadmore, this, false);
        mProgressView = (ProgressBar) mFooterView
                .findViewById(R.id.progress_view);

        addFooterView(mFooterView);

        super.setOnScrollListener(this);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    /**
     * Set the listener that will receive notifications every time the list
     * scrolls.
     *
     * @param l The scroll listener.
     */
    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mOnScrollListener = l;
    }

    /**
     * Register a callback to be invoked when this list reaches the end (last
     * item be visible)
     *
     * @param onLoadMoreListener The callback to run.
     */

    public void setOnLoadMoreListener(EndlessScrollListener.OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(view, firstVisibleItem,
                    visibleItemCount, totalItemCount);
        }


        if (mOnLoadMoreListener != null) {
            if (visibleItemCount == totalItemCount) {
                mProgressView.setVisibility(View.GONE);
                return;
            }

            if (totalItemCount > previousTotal) {
                mLoading = false;
                previousTotal = totalItemCount;
            }

            if (!mLoading
                    && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold) && mCurrentScrollState != SCROLL_STATE_IDLE) {
                mProgressView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mOnLoadMoreListener.onLoadMore();
                    }
                }, 1000);

                mLoading = true;
            }
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            view.invalidateViews();
        }

        mCurrentScrollState = scrollState;

        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }

    }

    public void onLoadMore() {
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadMore();
        }
    }

    /**
     * Notify the loading more operation has finished
     */
    public void onLoadMoreComplete() {
        mLoading = false;
        mProgressView.setVisibility(View.GONE);
    }

    public boolean isProgressVisible() {
        return mProgressView.getVisibility() == View.VISIBLE;
    }
}
