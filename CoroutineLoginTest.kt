lifecycleScope.launch {
                try{
                    val response = RetrofitLoginInstance.api.login(user)

                    if (response.token.isNotBlank()) {
                        val sharedPref = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
                        sharedPref.edit {
                            putString("TOKEN", response.token)
                            putString("EMAIL", user.email)
                            putString("PASSWORD", user.password)
                            putString("CLIENT", response.user.client)
                            putString("USERNAME", response.user.username)
                            putInt("ID", response.user.id ?: -1)
                            putString("MODULES", response.user.modules?.joinToString(","))
                        }

                        Toast.makeText(this@LoginActivity, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                    else {
                        Toast.makeText(this@LoginActivity, "Erro ao fazer login", Toast.LENGTH_SHORT).show()
                        Log.e("Login_ERROR", "Token recebido é nulo ou vazio")
                    }
                } catch (e: Exception) {}
            }
