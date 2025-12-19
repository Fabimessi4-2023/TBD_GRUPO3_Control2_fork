<template>
  <v-container class="login-bg" style="height: 100vh" fluid>
    <v-row class="fill-height" align="center" justify="center">
      <v-col cols="12" sm="10" md="6" lg="4">
        <v-card class="pa-6" elevation="8" rounded="lg">
          <div class="d-flex align-center justify-space-between mb-6">
            <div>
              <div class="text-h5 font-weight-bold">Bienvenido</div>
              <div class="text-body-2 text-medium-emphasis">Inicia sesión para continuar</div>
            </div>
            <v-avatar color="primary" variant="tonal">
              <v-icon icon="mdi-lock-outline" />
            </v-avatar>
          </div>

          <v-form ref="form" @submit.prevent="onSubmit">
            <v-alert
              v-if="errorMessage"
              type="error"
              variant="tonal"
              class="mb-4"
              density="comfortable"
            >
              {{ errorMessage }}
            </v-alert>

            <v-text-field
              v-model.trim="email"
              label="Usuario"
              type="username"
              autocomplete="username"
              prepend-inner-icon="mdi-account-outline"
              variant="outlined"
              :rules="emailRules"
              class="mb-3"
            />

            <v-text-field
              v-model="password"
              :type="showPassword ? 'text' : 'password'"
              label="Contraseña"
              autocomplete="current-password"
              prepend-inner-icon="mdi-lock-outline"
              :append-inner-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
              @click:append-inner="showPassword = !showPassword"
              variant="outlined"
              :rules="passwordRules"
              class="mb-2"
            />

            <div class="d-flex align-center justify-space-between mb-6">
              <v-checkbox v-model="remember" density="compact" hide-details label="Recordarme" />
              <v-btn variant="text" density="comfortable" class="text-none" @click="onForgotPassword">
                ¿Olvidaste tu contraseña?
              </v-btn>
            </div>

            <v-btn
              type="submit"
              color="primary"
              size="large"
              block
              :loading="loading"
              :disabled="loading"
            >
              Entrar
            </v-btn>

            <v-btn
              class="mt-3"
              variant="outlined"
              size="large"
              block
              @click="$router.push('/register')"
            >
              Crear cuenta
            </v-btn>
          </v-form>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import AuthService from '@/services/login'

export default {
  name: 'LoginView',
  data() {
    return {
      email: '',
      password: '',
      remember: true,
      showPassword: false,
      loading: false,
      errorMessage: '',
      emailRules: [v => !!v || 'El usuario es obligatorio'],
      passwordRules: [
        v => !!v || 'La contraseña es obligatoria',
        v => (v && v.length >= 6) || 'Mínimo 6 caracteres',
      ],
    }
  },
  methods: {
    async onSubmit() {
      const ok = await this.$refs.form?.validate?.()
      if (!ok?.valid) return

      this.loading = true
      this.errorMessage = ''
      try {
        await AuthService.login({
          username: this.email,
          password: this.password,
        })

        this.$router.push('/tasks')
      } catch (err) {
        const message =
          err?.response?.data?.message ||
          err?.response?.data?.error ||
          err?.message ||
          'No se pudo iniciar sesión.'
        this.errorMessage = message
      } finally {
        this.loading = false
      }
    },
    onForgotPassword() {
      // Placeholder (puedes enrutar a /forgot-password o abrir un dialog)
      alert('Función pendiente')
    },
  },
}
</script>

<style scoped>
.login-bg {
  background: radial-gradient(1200px circle at 20% 10%, rgba(25, 118, 210, 0.12), transparent 50%),
    radial-gradient(900px circle at 80% 30%, rgba(0, 150, 136, 0.10), transparent 55%),
    linear-gradient(180deg, #fafafa, #f5f5f5);
}
</style>
