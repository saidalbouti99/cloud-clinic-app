package com.example.cloudclinic_said;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private int[] mId;
    private LayoutInflater mInflater;
    private ViewPager2 viewPager2;
    private Context currentcontext;

    private String[] changeablestr = {"You have make payment first before consultation!",
            "Confirm payment details",
            "Redirecting..."};

    private String[] bankArr = { "Choose Bank", "Maybank", "CIMB", "Bank Islam"};

    private String[] websiteArr = {"http://www.google.com",
            "https://www.maybank2u.com.my/home/m2u/common/login.do",
            "https://www.cimbclicks.com.my/",
            "https://www.bankislam.biz/"};

    private Integer[] imageArray = { R.drawable.fpx, R.drawable.maybank, R.drawable.cimb,
            R.drawable.bankislam };

    private boolean cardDataInvalid = true;
    private Toast toaster;
    private Card userCard;
    private Bank userBank;

    ViewPagerAdapter(Context context, int[] id, ViewPager2 viewPager2) {
        this.mInflater = LayoutInflater.from(context);
        this.mId = id;
        this.viewPager2 = viewPager2;
        this.currentcontext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_viewpager, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(position == 0){
            LayoutInflater vi = (LayoutInflater) currentcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.page1_content, null);

            //Spinner COde//

            Button nextbtn = (Button) v.findViewById(R.id.continuebtn);

            final Spinner spinner = (Spinner) v.findViewById(R.id.bankspinner);

            SpinnerAdapter adapter = new SpinnerAdapter(currentcontext, R.layout.spinnerlayout, bankArr, imageArray);
            spinner.setAdapter(adapter);

            //-----------------------------------------------------------------------------------------//
            //CardView Code//

            CardView cardv;
            final ExpandableRelativeLayout expandableLayout;
            final Button okbtn;
            final TextView cardinsert;
            final List<EditText> cardtxts = new ArrayList<>();

            cardv = (CardView) v.findViewById(R.id.creditCard);
            expandableLayout = (ExpandableRelativeLayout) v.findViewById(R.id.expandMenu);
            okbtn = (Button) expandableLayout.findViewById(R.id.okbtn);
            cardinsert = (TextView) cardv.findViewById(R.id.addcard);

            cardtxts.add((EditText) expandableLayout.findViewById(R.id.editTextTextPersonName));
            cardtxts.add((EditText) expandableLayout.findViewById(R.id.editTextCardNo));
            cardtxts.add((EditText) expandableLayout.findViewById(R.id.editTextDate));
            cardtxts.add((EditText) expandableLayout.findViewById(R.id.editTextCVV));
            cardtxts.add((EditText) expandableLayout.findViewById(R.id.editTextBillingAdd));

            cardv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(EditText e : cardtxts){
                        if( e.getText().toString().matches("") || TextUtils.isEmpty(e.getText().toString())){
                            toaster = Toast.makeText(currentcontext,"Card Data is Invalid",Toast.LENGTH_SHORT);
                            toaster.show();
                            cardDataInvalid = true;
                            userCard = null;
                            cardinsert.setText("Add Card");
                            break;
                        }
                        else{
                            cardDataInvalid = false;
                        }
                    }

                    if(!cardDataInvalid){
                        cardinsert.setText("Card Added");
                        userCard = new Card (cardtxts.get(0).getText().toString(),
                                cardtxts.get(1).getText().toString(),
                                cardtxts.get(2).getText().toString(),
                                cardtxts.get(3).getText().toString(),
                                cardtxts.get(4).getText().toString());
                    }

                    expandableLayout.toggle();
                }
            });

            okbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // checks also if user input anything..
                    for(EditText e : cardtxts){
                        if( e.getText().toString().matches("") || TextUtils.isEmpty(e.getText().toString())){
                            toaster = Toast.makeText(currentcontext,"Card Data is Invalid",Toast.LENGTH_SHORT);
                            toaster.show();
                            cardDataInvalid = true;
                            userCard = null;
                            cardinsert.setText("Add Card");
                            break;
                        }
                        else{
                            cardDataInvalid = false;
                        }
                    }

                    if(!cardDataInvalid){
                        cardinsert.setText("Card Added");
                        userCard = new Card (cardtxts.get(0).getText().toString(),
                                cardtxts.get(1).getText().toString(),
                                cardtxts.get(2).getText().toString(),
                                cardtxts.get(3).getText().toString(),
                                cardtxts.get(4).getText().toString());
                    }

                    expandableLayout.toggle();

                }
            });

            //--------------------------------------------------------------------------------//
            // Nextbtn : Checks if user has input data, if yes proceed, if null, show toaster

            nextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(spinner.getSelectedItemPosition() != 0 && cardDataInvalid){
                        int pos = spinner.getSelectedItemPosition();
                        userBank = new Bank(imageArray[pos], bankArr[pos], websiteArr[pos]);
                        userCard = null;
                        viewPager2.setCurrentItem(1);
                    }
                    else if(spinner.getSelectedItemPosition() == 0 && !cardDataInvalid){
                        userBank = null;
                        viewPager2.setCurrentItem(1);
                    }
                    else if(spinner.getSelectedItemPosition() != 0 && !cardDataInvalid){
                        toaster = Toast.makeText(currentcontext,"Please select only one payment method",Toast.LENGTH_SHORT);
                        toaster.show();
                    }
                    else{
                        userBank = null;
                        userCard = null;
                        toaster = Toast.makeText(currentcontext,"Please specify your payment method",Toast.LENGTH_SHORT);
                        toaster.show();
                    }
                }
            });

            ViewGroup insertPoint = (ViewGroup) holder.rcontent;
            insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        else if(position == 1){
            LayoutInflater vi = (LayoutInflater) currentcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.page2_content, null);

            final Button confirmbtn = v.findViewById(R.id.confirmbtn);
            final ImageView imgtype = v.findViewById(R.id.cardbank_imgview);
            final TextView paytype = v.findViewById(R.id.paymentType);
            final TextView payid = v.findViewById(R.id.bankName);

            if(userCard != null && userBank == null){
                imgtype.setImageDrawable(currentcontext.getDrawable(R.drawable.ic_baseline_credit_card_24));
                paytype.setText("Credit/Debit Card");
                payid.setText(userCard.cardNum);
            }
            else if(userBank != null && userCard == null)
            {
                imgtype.setImageDrawable(currentcontext.getDrawable(userBank.imgBank));
                paytype.setText("Online Banking");
                payid.setText(userBank.nameBank);
            }

            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);

                    switch(position){
                        case 0:
                            userBank = null;
                            userCard = null;
                            break;
                        case 1:

                            if(userCard != null && userBank == null){
                                imgtype.setImageDrawable(currentcontext.getDrawable(R.drawable.ic_baseline_credit_card_24));
                                paytype.setText("Credit/Debit Card");
                                payid.setText(userCard.cardNum);
                            }
                            else if(userBank != null && userCard == null)
                            {
                                imgtype.setImageDrawable(currentcontext.getDrawable(userBank.imgBank));
                                paytype.setText("Online Banking");
                                payid.setText(userBank.nameBank);
                            }
                            break;
                        case 2:
                            String page = "http://www.google.com";

                            if(userBank != null)
                                page = userBank.website;

                            Activity a = (Activity)currentcontext;

                            Uri webpage = Uri.parse(page);

                            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);

                            if(webIntent.resolveActivity(currentcontext.getPackageManager()) != null){
                                a.startActivityForResult(webIntent, 2);
                            }
                            else
                                Toast.makeText(currentcontext, "Sorry, no app can handle this action and data.", Toast.LENGTH_SHORT).show();

                        default:
                            break;
                    }
                }
            });

            confirmbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager2.setCurrentItem(2);
                }
            });

            ViewGroup insertPoint = (ViewGroup) holder.rcontent;
            insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        }
        else if(position == 2){
            holder.title.setText("");
        }
        holder.myTextView.setText(changeablestr[position]);

    }

    @Override
    public int getItemCount() {
        return mId.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView myTextView;
        RelativeLayout relativeLayout;
        RelativeLayout rcontent;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.chooseTv);
            myTextView = itemView.findViewById(R.id.changeable_tv);
            relativeLayout = itemView.findViewById(R.id.container);
            rcontent = itemView.findViewById(R.id.stuffpt);
        }
    }

}

