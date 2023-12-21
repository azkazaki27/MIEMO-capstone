package com.capstone.miemo.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.capstone.miemo.R
import com.capstone.miemo.ui.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var tvDate: TextView
    private lateinit var tvMemo: TextView
    private lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val memoId = intent.getIntExtra("MEMO_ID", -1)

        // Inisialisasi ViewModel
        val detailViewModel = ViewModelProvider(this, ViewModelFactory(application)).get(DetailViewModel::class.java)

        // Inisialisasi TextView di halaman detail
        tvDate = findViewById(R.id.tv_date)
        tvMemo = findViewById(R.id.tv_memo)
        backButton = findViewById(R.id.btn_back)

        // Observe LiveData untuk memo yang sesuai dengan memoId
        detailViewModel.getMemoById(memoId).observe(this) { memo ->
            // Check if memo is not null before accessing its properties
            if (memo != null) {
                // Update TextView di halaman detail dengan informasi memo
                tvDate.text = memo.date // Assuming you have a getDate() method in your Memo class
                tvMemo.text = memo.memo // Assuming you have a getMemo() method in your Memo class
            } else {
                // Handle the case where memo is null, e.g., show an error message or navigate back
            }
        }

        backButton.setOnClickListener { finish() }
    }
}
