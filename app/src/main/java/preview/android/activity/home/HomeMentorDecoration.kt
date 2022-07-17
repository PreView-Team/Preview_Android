package preview.android.activity.home

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HomeMentorDecoration(context : Context) : RecyclerView.ItemDecoration() {

    private val context = context

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        if(position ==0){
            outRect.right = dpToPx(context, 20)
            outRect.left = dpToPx(context, 20)
        }
        else{
            outRect.right = dpToPx(context, 20)
            outRect.left = dpToPx(context, 0)
        }

    }

    private fun dpToPx(context: Context, dp : Int) : Int{
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(), context.resources.displayMetrics
        ).toInt()
    }
}