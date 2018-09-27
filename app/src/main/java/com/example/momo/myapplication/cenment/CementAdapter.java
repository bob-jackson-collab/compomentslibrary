package com.example.momo.myapplication.cenment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Pair;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.example.momo.myapplication.R;
import com.example.momo.myapplication.cenment.eventhook.EventHook;
import com.example.momo.myapplication.cenment.eventhook.EventHookHelper;
import com.example.momo.myapplication.cenment.eventhook.OnClickEventHook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static android.view.View.NO_ID;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/18
 *   desc: MyApplication
 * </pre>
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class CementAdapter extends RecyclerView.Adapter<CementViewHolder> {

    public static final String TAG = CementAdapter.class.getSimpleName();
    private static final String SAVED_STATE_ARG_VIEW_HOLDERS = "saved_state_view_holders";

    private final ModelList mCementModels = new ModelList();
    private final EventHookHelper mEventHookHelper = new EventHookHelper(this);
    private boolean mIsAttached = false;

    private final LongSparseArray<CementViewHolder> mBindViewHolders = new LongSparseArray<>();
    private ViewHolderState mViewHolderState = new ViewHolderState();

    private final GridLayoutManager.SpanSizeLookup mSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            CementModel cementModel = mCementModels.get(position);
            return cementModel != null ? cementModel.getSpanSize(mSpanCount, position, getItemCount()) : 1;
        }
    };

    private int mSpanCount = 1;

    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return mSpanSizeLookup;
    }

    public int getSpanCount() {
        return mSpanCount;
    }

    public void setSpanCount(int spanCount) {
        mSpanCount = spanCount;
    }

    protected CementAdapter() {
        /**
         * 在使用RecyclerView时，难免会用到adapter的notifyDataSetChanged方法来更新数据，
         * 其实notify**Changed系列方法都存在一个已知的焦点丢失的bug，
         * 如果在notify之后重新手动requestFocus，又会导致焦点可能不对应的问题。
         */
        setHasStableIds(true);
        mSpanSizeLookup.setSpanIndexCacheEnabled(true);
    }

    @Nullable
    public CementModel<?> getModel(int position) {
        return position >= 0 && position < mCementModels.size() ? mCementModels.get(position) : null;
    }

    public boolean containsModel(CementModel<?> model) {
        return mCementModels.indexOf(model) >= 0;
    }

    public int indexOfModel(CementModel<?> model) {
        return mCementModels.indexOf(model);
    }

    @NonNull
    protected List<CementModel<?>> getAllModelSubListAfter(@Nullable CementModel<?> model) {
        int index = mCementModels.indexOf(model);
        if (index == -1) return Collections.emptyList();
        return mCementModels.subList(index + 1, mCementModels.size());
    }

    @NonNull
    public List<CementModel<?>> getAllModelListBetween(@Nullable CementModel<?> start, @Nullable CementModel<?> end) {
        int startIdx = mCementModels.indexOf(start),
                endIdx = mCementModels.indexOf(end);
        startIdx = startIdx == -1 ? 0 : startIdx + 1;
        endIdx = endIdx == -1 ? mCementModels.size() : endIdx;
        if (startIdx > endIdx) return Collections.emptyList();

        return new ArrayList<>(mCementModels.subList(startIdx, endIdx));
    }

    public void addModel(@NonNull CementModel<?> model) {
        final int initialSize = mCementModels.size();
        mCementModels.add(model);
        notifyItemInserted(initialSize);
    }

    public void addModel(int index, @NonNull CementModel<?> model) {
        if (index > mCementModels.size() || index < 0) {
            return;
        }
        mCementModels.add(index, model);
        notifyItemInserted(index);
    }

    public void addModels(@NonNull CementModel<?>... models) {
        addModels(Arrays.asList(models));
    }

    public void addModels(@NonNull Collection<? extends CementModel<?>> models) {
        final int initialSize = mCementModels.size();
        mCementModels.addAll(models);
        notifyItemRangeInserted(initialSize, models.size());
    }

    public void insertModelBefore(@NonNull CementModel<?> modelToInsert, @Nullable CementModel<?> modelToInsertBefore) {
        int targetIndex = mCementModels.indexOf(modelToInsertBefore);
        if (targetIndex == -1) {
            return;
        }
        mCementModels.add(targetIndex, modelToInsert);
        notifyItemInserted(targetIndex);
    }

    public void insertModelsBefore(@NonNull Collection<? extends CementModel<?>> modelsToInsert, @Nullable CementModel<?> modelToInsertBefore) {
        int targetIndex = mCementModels.indexOf(modelToInsertBefore);
        if (targetIndex == -1) {
            return;
        }
        mCementModels.addAll(targetIndex, modelsToInsert);
        notifyItemRangeInserted(targetIndex, modelsToInsert.size());
    }

    public void notifyModelChanged(@NonNull CementModel<?> model) {
        notifyModelChanged(model, null);
    }

    public void notifyModelChanged(@NonNull CementModel<?> model, @Nullable Object payload) {
        int index = mCementModels.indexOf(model);
        if (index != -1) {
            notifyItemChanged(index, payload);
        }
    }

    public void removeModel(@Nullable CementModel<?> modelToRemove) {
        int index = mCementModels.indexOf(modelToRemove);
        if (index >= 0 && index < mCementModels.size()) {
            mCementModels.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void removeAllModels() {
        final int initialSize = mCementModels.size();
        mCementModels.clear();
        notifyItemRangeRemoved(0, initialSize);
    }

    public void replaceAllModels(@NonNull final List<? extends CementModel<?>> models) {
        if (models.size() == 0) {
            addModels(models);
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                private <T> T getOrNull(@Nullable List<T> list, int index) {
                    return (list != null && index >= 0 && index < list.size()) ? list.get(index) : null;
                }

                @Override
                public int getOldListSize() {
                    return mCementModels.size();
                }

                @Override
                public int getNewListSize() {
                    return models.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    CementModel<?> oldModel = getOrNull(mCementModels, oldItemPosition);
                    CementModel<?> newModel = getOrNull(models, newItemPosition);
                    return oldModel != null && newModel != null
                            && oldModel.getClass().equals(newModel.getClass())
                            && oldModel.isItemTheSame(newModel);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    CementModel<?> oldModel = getOrNull(mCementModels, oldItemPosition),
                            newModel = getOrNull(models, newItemPosition);
                    return oldModel != null && newModel != null
                            && oldModel.getClass().equals(newModel.getClass())
                            && oldModel.isContentTheSame(newModel);
                }
            });
            mCementModels.clear();
            mCementModels.addAll(models);
            result.dispatchUpdatesTo(this);
        }
    }

    public void replaceAllModels(@NonNull final List<? extends CementModel<?>> modelsToReplace, boolean detectMove) {
        if (mCementModels.size() == 0) {
            addModels(modelsToReplace);
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                private <T> T getOrNull(@Nullable List<T> list, int index) {
                    return (list != null && index >= 0 && index < list.size())
                            ? list.get(index) : null;
                }

                @Override
                public int getOldListSize() {
                    return mCementModels.size();
                }

                @Override
                public int getNewListSize() {
                    return modelsToReplace.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    CementModel<?> oldModel = getOrNull(mCementModels, oldItemPosition),
                            newModel = getOrNull(modelsToReplace, newItemPosition);
                    return oldModel != null && newModel != null
                            && oldModel.getClass().equals(newModel.getClass())
                            && oldModel.isItemTheSame(newModel);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    CementModel<?> oldModel = getOrNull(mCementModels, oldItemPosition),
                            newModel = getOrNull(modelsToReplace, newItemPosition);
                    return oldModel != null && newModel != null
                            && oldModel.getClass().equals(newModel.getClass())
                            && oldModel.isContentTheSame(newModel);
                }
            }, detectMove);
            mCementModels.clear();
            mCementModels.addAll(modelsToReplace);
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public CementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        CementViewHolder viewHolder = mCementModels.viewHolderFactory.create(viewType, viewGroup);
        mEventHookHelper.bind(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CementViewHolder cementViewHolder, int position) {
        onBindViewHolder(cementViewHolder, position);
    }

    @Override
    public void onBindViewHolder(@NonNull CementViewHolder holder, int position, @NonNull List<Object> payloads) {
        CementModel<?> cementModel = mCementModels.get(position);
        if (holder == null || cementModel == null) {
            return;
        }
        if (mBindViewHolders.get(holder.getItemId()) != null) {
            mViewHolderState.save(mBindViewHolders.get(holder.getItemId()));
        }
        holder.bind(cementModel, payloads);
        mViewHolderState.restore(holder);
        mBindViewHolders.put(holder.getItemId(), holder);
    }

    @Override
    public void onViewRecycled(@NonNull CementViewHolder holder) {
        mViewHolderState.save(holder);
        mBindViewHolders.remove(holder.getItemId());
        holder.unbind();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull CementViewHolder holder) {
        CementModel cementModel = holder.getCementModel();
        if (cementModel == null) {
            return;
        }
        cementModel.attachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull CementViewHolder holder) {
        CementModel cementModel = holder.getCementModel();
        if (cementModel == null) {
            return;
        }
        cementModel.detachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        return mCementModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        CementModel cementModel = mCementModels.get(position);
        return cementModel == null ? NO_ID : cementModel.getViewType();
    }

    @Override
    public long getItemId(int position) {
        CementModel cementModel = mCementModels.get(position);
        return cementModel == null ? NO_ID : cementModel.id();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mIsAttached = true;
    }

    public void onSaveInstanceState(Bundle outState) {
        for (int i = 0; i < mBindViewHolders.size(); i++) {
            long key = mBindViewHolders.keyAt(i);
            mViewHolderState.save(mBindViewHolders.get(key));
        }
        if (mViewHolderState.size() > 0 && !hasStableIds()) {
            throw new IllegalStateException("Must have stable ids when saving view holder state");
        }
        outState.putParcelable(SAVED_STATE_ARG_VIEW_HOLDERS, mViewHolderState);
    }

    public void onRestoreInstanceState(@Nullable Bundle inState) {
        // To simplify things we enforce that state is restored before views are bound, otherwise it
        // is more difficult to update view state once they are bound
        if (mBindViewHolders.size() > 0) {
            throw new IllegalStateException(
                    "State cannot be restored once views have been bound. It should be done before adding "
                            + "the adapter to the recycler view.");
        }

        if (inState != null) {
            ViewHolderState savedState = inState.getParcelable(SAVED_STATE_ARG_VIEW_HOLDERS);
            if (savedState != null) {
                mViewHolderState = savedState;
            } else {
                Log.w(TAG, "can not get save viewholder state");
            }
        }
    }

    public <VH extends CementViewHolder> void addEventHook(@NonNull EventHook<VH> eventHook) {
        if (mIsAttached) {
            Log.w(TAG, "addEventHook is called after adapter attached");
        }
        // noinspection unchecked
        mEventHookHelper.add(eventHook);
    }
    //</editor-fold>

    //<editor-fold desc="OnClickListener">
    @Nullable
    private OnItemClickListener onItemClickListener;
    @Nullable
    private EventHook<CementViewHolder> onItemClickEventHook;

    private void addOnItemClickEventHook() {
        onItemClickEventHook = new OnClickEventHook<CementViewHolder>(
                CementViewHolder.class) {
            @Override
            public void onClick(@NonNull View view, @NonNull CementViewHolder viewHolder,
                                int position, @NonNull CementModel rawModel) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(view, viewHolder, position, rawModel);
                }
            }

            @Nullable
            @Override
            public View onBind(@NonNull CementViewHolder viewHolder) {
                return viewHolder.itemView.isClickable() ? viewHolder.itemView : null;
            }
        };
        addEventHook(onItemClickEventHook);
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener onItemClickListener) {
        if (mIsAttached && onItemClickEventHook == null && onItemClickListener != null) {
            throw new IllegalStateException("setOnItemClickListener " + "must be called before the RecyclerView#setAdapter");
        } else if (!mIsAttached && onItemClickEventHook == null) {
            addOnItemClickEventHook();
        }
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Interface definition for a callback to be invoked when a model is clicked.
     */
    public interface OnItemClickListener {
        /**
         * Called when a model has been clicked.
         *
         * @param itemView   The view that was clicked.
         * @param viewHolder The viewHolder that was clicked.
         * @param position   Adapter position of the viewHolder,
         *                   see {@link RecyclerView.ViewHolder#getAdapterPosition()}.
         * @param model      The model that was clicked.
         */
        void onClick(@NonNull View itemView, @NonNull CementViewHolder viewHolder,
                     int position, @NonNull CementModel<?> model);
    }

    public static abstract class WrapperViewHolderCreator<VH extends CementWrapperViewHolder<MVH>,
            MVH extends CementViewHolder> implements IViewHolderCreator<VH> {
        @LayoutRes
        private final int childLayoutRes;
        @NonNull
        private final IViewHolderCreator<MVH> childViewHolderCreator;

        public WrapperViewHolderCreator(@LayoutRes int childLayoutRes, @NonNull IViewHolderCreator<MVH> childViewHolderCreator) {
            this.childLayoutRes = childLayoutRes;
            this.childViewHolderCreator = childViewHolderCreator;
        }

        @NonNull
        @Override
        public VH create(@NonNull View view) {
            ViewStub viewStub = (ViewStub) view.findViewById(R.id.view_model_child_stub);
            if (viewStub == null) {
                throw new IllegalStateException("layout must have ViewStub{id=view_model_child_stub}");
            }
            viewStub.setLayoutResource(childLayoutRes);
            return create(view, childViewHolderCreator.create(viewStub.inflate()));
        }

        public abstract VH create(@NonNull View view, MVH childViewHolder);
    }

    private class ModelList extends ArrayList<CementModel<?>> {
        private final ViewHolderFactory viewHolderFactory = new ViewHolderFactory();

        @Override
        public boolean add(@NonNull CementModel<?> model) {
            viewHolderFactory.register(model);
            return super.add(model);
        }

        @Override
        public void add(int index, @NonNull CementModel<?> element) {
            viewHolderFactory.register(element);
            super.add(index, element);
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends CementModel<?>> c) {
            viewHolderFactory.register(c);
            return super.addAll(c);
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends CementModel<?>> c) {
            viewHolderFactory.register(c);
            return super.addAll(index, c);
        }
    }

    private static class ViewHolderFactory {
        private static SparseArray<Pair<Integer, IViewHolderCreator>> creatorSparseArray = new SparseArray<>();

        void register(@NonNull CementModel model) {
            int viewType = model.getViewType();
            if (viewType == NO_ID) {
                throw new RuntimeException("illegal viewType =" + viewType);
            }
            if (creatorSparseArray.get(viewType) == null) {
                creatorSparseArray.put(viewType, Pair.create(model.getLayoutRes(), model.getViewHolderCreator()));
            }
        }

        void register(@NonNull Collection<? extends CementModel> models) {
            for (CementModel model : models) {
                if (model == null) {
                    continue;
                }
                register(model);
            }
        }

        CementViewHolder create(@LayoutRes int viewType, @NonNull ViewGroup parent) {
            Pair<Integer, IViewHolderCreator> info = creatorSparseArray.get(viewType);
            if (info == null) {
                throw new RuntimeException("cannot find viewHolderCreator for viewType="
                        + viewType);
            }
            try {
                return info.second.create(LayoutInflater.from(parent.getContext()).inflate(info.first, parent, false));
            } catch (Exception e) {
                throw new RuntimeException("cannot inflate view="
                        + parent.getContext().getResources().getResourceName(info.first)
                        + "\nreason:" + e.getMessage(), e);
            }


        }
    }

    public interface IViewHolderCreator<VH extends CementViewHolder> {
        @NonNull
        VH create(@NonNull View view);
    }

}
