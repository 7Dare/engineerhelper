package com.example.engineerhelper.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.engineerhelper.R;
import com.example.engineerhelper.entity.Customer;

import java.util.List;

import android.widget.ImageButton;
import android.widget.TextView;
//azhheowatgsadawdasd
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<Customer> customerList;
    private Context context;
    private OnItemClickListener listener;

    // 接口用于点击事件
    public interface OnItemClickListener {
        void onEditClick(Customer customer);
        void onDeleteClick(Customer customer);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public CustomerAdapter(Context context, List<Customer> customerList) {
        this.context = context;
        this.customerList = customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
        notifyDataSetChanged();
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        Customer customer = customerList.get(position);
        holder.textViewName.setText(customer.getName());
        holder.textViewAddress.setText("地址: " + customer.getAddress());
        holder.textViewPhone.setText("电话: " + customer.getPhone());
        holder.textViewEmail.setText("邮箱: " + customer.getEmail());

        holder.buttonEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(customer);
            }
        });

        holder.buttonDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(customer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewAddress, textViewPhone, textViewEmail;
        ImageButton buttonEdit, buttonDelete;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
