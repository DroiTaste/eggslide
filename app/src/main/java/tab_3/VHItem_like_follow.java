package tab_3;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ultra.seed.eggslide.R;

class VHItem_like_follow extends RecyclerView.ViewHolder{

    ImageView profile_img;
    TextView content_txt;
    ImageView follow_btn;

    public VHItem_like_follow(View itemView) {
        super(itemView);
        this.profile_img = (ImageView)itemView.findViewById(R.id.profile_img);
        this.content_txt = (TextView)itemView.findViewById(R.id.content_txt);
        this.follow_btn = (ImageView)itemView.findViewById(R.id.follow_btn);
    }
}