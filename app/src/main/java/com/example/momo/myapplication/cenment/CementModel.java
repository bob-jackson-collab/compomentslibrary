package com.example.momo.myapplication.cenment;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import static android.view.View.NO_ID;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/18
 *   desc: MyApplication
 * </pre>
 */
public abstract class CementModel<T extends CementViewHolder> implements IDiffUtilHelper<CementModel<?>> {

    private static long sIdCounter = NO_ID - 1;
    private long mId;

    protected CementModel(long id) {
        mId = id;
    }

    public CementModel() {
        this(sIdCounter--);
    }

    public final long id() {
        return mId;
    }

    public int getSpanSize(int totalSpanCount, int position, int itemCount) {
        return 1;
    }

    protected boolean shouldSaveViewState() {
        return false;
    }

    @LayoutRes
    public abstract int getLayoutRes();

    protected int getViewType() {
       return hashInt(getLayoutRes());
    }

    public void bindData(@NonNull T holder) {

    }

    public void bindData(@NonNull T holder, @Nullable List<Object> payLoads) {
        bindData(holder);
    }

    public void unbind(T holder) {

    }

    public void attachedToWindow(@NonNull T holder) {

    }

    public void detachedFromWindow(@NonNull T holder) {

    }

    public abstract CementAdapter.IViewHolderCreator<T> getViewHolderCreator();

    @Override
    public boolean isItemTheSame(@NonNull CementModel<?> item) {
        return id() == item.id();
    }

    @Override
    public boolean isContentTheSame(@NonNull CementModel<?> item) {
        return true;
    }

    protected void id(long id) {
        if (id == -1) {
            return;
        }
        mId = id;
    }

    /**
     * Use multiple numbers as the id for this model.
     *
     * @param ids not allow null child, if found, use old id
     */
    protected void id(Number... ids) {
        long result = 0;
        for (Number id : ids) {
            if (id == null) return;
            result = 31 * result + hashLong64Bit(id.hashCode());
        }
        id(result);
    }

    /**
     * Use two numbers as model id.
     */
    protected void id(long id1, long id2) {
        long result = hashLong64Bit(id1);
        result = 31 * result + hashLong64Bit(id2);
        id(result);
    }

    /**
     * Use a string as the model id.
     *
     * @param key not allow null
     */
    protected void id(@Nullable CharSequence key) {
        if (key == null) return;

        id(hashString64Bit(key));
    }

    /**
     * Use several strings to define the id of the model.
     *
     * @param key       not allow null
     * @param otherKeys not allow null child, if found, use old id
     */
    protected void id(@Nullable CharSequence key, CharSequence... otherKeys) {
        if (key == null) return;

        long result = hashString64Bit(key);
        for (CharSequence otherKey : otherKeys) {
            if (otherKey == null) return;
            result = 31 * result + hashString64Bit(otherKey);
        }
        id(result);
    }

    /**
     * Use a string and a number as model id.
     *
     * @param key not allow null, if found, use old id
     */
    protected void id(@Nullable CharSequence key, long id) {
        if (key == null) return;

        long result = hashString64Bit(key);
        result = 31 * result + hashLong64Bit(id);
        id(result);
    }

    /**
     * Hash a long into 64 bits instead of the normal 32. This uses a xor shift implementation to
     * attempt psuedo randomness so object ids have an even spread for less chance of collisions.
     * <p>
     * From http://stackoverflow.com/a/11554034
     * <p>
     * http://www.javamex.com/tutorials/random_numbers/xorshift.shtml
     */
    private static long hashLong64Bit(long value) {
        value ^= (value << 21);
        value ^= (value >>> 35);
        value ^= (value << 4);
        return value;
    }

    private static int hashInt(int value) {
        value ^= (value << 13);
        value ^= (value >>> 17);
        value ^= (value << 5);
        return value;
    }

    /**
     * Hash a string into 64 bits instead of the normal 32. This allows us to better use strings as a
     * model id with less chance of collisions. This uses the FNV-1a algorithm for a good mix of speed
     * and distribution.
     * <p>
     * Performance comparisons found at http://stackoverflow.com/a/1660613
     * <p>
     * Hash implementation from http://www.isthe.com/chongo/tech/comp/fnv/index.html#FNV-1a
     */
    private static long hashString64Bit(@NonNull CharSequence str) {
        long result = 0xcbf29ce484222325L;
        final int len = str.length();
        for (int i = 0; i < len; i++) {
            result ^= str.charAt(i);
            result *= 0x100000001b3L;
        }
        return result;
    }
}
