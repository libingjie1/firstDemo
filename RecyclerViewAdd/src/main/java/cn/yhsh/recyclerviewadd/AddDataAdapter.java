package cn.yhsh.recyclerviewadd;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * @author 轻飞扬
 */
public class AddDataAdapter extends RecyclerView.Adapter<AddDataAdapter.VH> {
    private List<String> addViewed;
    private final int Normal = 1;
    private final int FooterNum = 2;
    private View footerView;
    private View normalView;

    public AddDataAdapter(List<String> addViewed) {
        this.addViewed = addViewed;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == FooterNum) {
            footerView = View.inflate(viewGroup.getContext(), R.layout.activity_footer_view, null);
            return new VH(footerView);
        } else {
            normalView = View.inflate(viewGroup.getContext(), R.layout.activity_normal_view, null);
            return new VH(normalView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final VH vh, final int i) {
        if (addViewed.size() != i) {
            vh.tvClickAddTextButton.setText(addViewed.get(i));
            vh.tvClickAddTextButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClickListenerRemove.setLongClickListener(v);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return addViewed.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == addViewed.size()) {
            return FooterNum;
        } else {
            return Normal;
        }
    }

    class VH extends RecyclerView.ViewHolder {
        private TextView tvClickAddTextButton;
        private TextView tvAddTextButton;

        public VH(@NonNull final View itemView) {
            super(itemView);
            if (itemView == normalView) {
                tvClickAddTextButton = (TextView) itemView.findViewById(R.id.tv_click_add_text);
            } else {
                tvAddTextButton = (TextView) itemView.findViewById(R.id.tv_add_text_button);
                tvAddTextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(itemView.getContext(), "点击了添加按钮", Toast.LENGTH_SHORT).show();
                        addDataListener.onAddDataListener(getLayoutPosition());
                    }
                });
            }
        }
    }

    addDataListener addDataListener;
    longClickListenerRemove longClickListenerRemove;

    public void setLongClickListenerRemove(AddDataAdapter.longClickListenerRemove longClickListenerRemove) {
        this.longClickListenerRemove = longClickListenerRemove;
    }

    public void setAddDataListener(AddDataAdapter.addDataListener addDataListener) {
        this.addDataListener = addDataListener;
    }

    interface addDataListener {
        void onAddDataListener(int position);
    }

    interface longClickListenerRemove {
        void setLongClickListener(View view);
    }
}
