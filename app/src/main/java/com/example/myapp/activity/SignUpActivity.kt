package com.example.myapp.activity

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.myapp.databinding.ActivitySignUpBinding
import com.example.myapp.process.RetrofitClient
import com.example.myapp.process.login.RegisterRequest
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch




class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var btnSignIn: Button
    private lateinit var imgbtnReturn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViews()
        setupView()
    }

    private fun initViews() {
        btnSignIn = binding.btnRegister
        imgbtnReturn = binding.imgbtnReturn
    }

    private fun setupView() {
        btnSignIn.setOnClickListener {
            val name = binding.edtName.text.toString()
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            val repeatPassword = binding.edtRepeatPassword.text.toString()
            if (checkInfor(name, username , password, repeatPassword)) {
                lifecycleScope.launch {
                    try {
                        val request = RegisterRequest(
                            name = name,
                            username = username,
                            password = password,
                            repeatpassword = repeatPassword
                        )

                        val response = RetrofitClient.authService.register(request)

                        when (response.code()) {
                            201 -> {
                                val bodyString = response.body()?.string()
                                Snackbar.make(binding.root, bodyString ?: "Đăng ký thành công", Snackbar.LENGTH_SHORT).show()
                                btnSignIn.postDelayed({
                                    finish()
                                }, 1500)
                            }

                            400 -> Snackbar.make(btnSignIn, "Yêu cầu không hợp lệ", Snackbar.LENGTH_SHORT).show()
                            409 -> Snackbar.make(btnSignIn, "Tên đăng nhập đã tồn tại", Snackbar.LENGTH_SHORT).show()
                            else -> Snackbar.make(
                                btnSignIn,
                                "Lỗi không xác định: ${response.code()}",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        Snackbar.make(btnSignIn, "Lỗi máy chủ hoặc kết nối", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                binding.edtName.setText("")
                binding.edtUsername.setText("")
                binding.edtPassword.setText("")
                binding.edtRepeatPassword.setText("")
            }
        }
        imgbtnReturn.setOnClickListener {
            finish()
        }
    }

    private fun checkInfor(name: String, username: String, password: String, repeatPassword: String): Boolean {
        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            Snackbar.make(btnSignIn, "Vui lòng nhập đầy đủ thông tin", Snackbar.LENGTH_SHORT).show()
            return false
        } else if (password != repeatPassword) {
            Snackbar.make(btnSignIn, "Mật khẩu không khớp", Snackbar.LENGTH_SHORT).show()
            return false
        } else return true
    }
}
