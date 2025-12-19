<template>
  <v-container class="login-bg" style="height: 100vh" fluid>
    <v-row class="fill-height" align="center" justify="center">
      <v-col cols="12" sm="10" md="6" lg="4">
        <v-card class="pa-6" elevation="8" rounded="lg">
          <div class="d-flex align-center justify-space-between mb-6">
            <div>
              <div class="text-h5 font-weight-bold">Crear cuenta</div>
              <div class="text-body-2 text-medium-emphasis">Regístrate en segundos</div>
            </div>
            <v-avatar color="primary" variant="tonal">
              <v-icon icon="mdi-account-plus-outline" />
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
              v-model.trim="username"
              label="Usuario"
              autocomplete="username"
              prepend-inner-icon="mdi-account-outline"
              variant="outlined"
              :rules="usernameRules"
              class="mb-3"
            />

            <v-text-field
              v-model="password"
              :type="showPassword ? 'text' : 'password'"
              label="Contraseña"
              autocomplete="new-password"
              prepend-inner-icon="mdi-lock-outline"
              :append-inner-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
              @click:append-inner="showPassword = !showPassword"
              variant="outlined"
              :rules="passwordRules"
              class="mb-3"
            />

            <v-text-field
              v-model="confirmPassword"
              :type="showConfirm ? 'text' : 'password'"
              label="Repetir contraseña"
              autocomplete="new-password"
              prepend-inner-icon="mdi-lock-check-outline"
              :append-inner-icon="showConfirm ? 'mdi-eye-off' : 'mdi-eye'"
              @click:append-inner="showConfirm = !showConfirm"
              variant="outlined"
              :rules="confirmRules"
              class="mb-4"
            />

            <v-row dense class="mb-2">
              <v-col cols="12" sm="6">
                <v-text-field
                  v-model.number="latitud"
                  label="Latitud"
                  type="number"
                  variant="outlined"
                  :rules="coordRules"
                  density="comfortable"
                />
              </v-col>
              <v-col cols="12" sm="6">
                <v-text-field
                  v-model.number="longitud"
                  label="Longitud"
                  type="number"
                  variant="outlined"
                  :rules="coordRules"
                  density="comfortable"
                />
              </v-col>
            </v-row>

            <div class="map-wrap mb-4">
              <div class="text-caption text-medium-emphasis mb-2">
                Selecciona tu ubicación en el mapa (click).
              </div>
              <div ref="mapEl" class="leaflet-map" />
            </div>

            <v-btn type="submit" color="primary" size="large" block :loading="loading">
              Registrarme
            </v-btn>

            <v-btn class="mt-3" variant="text" size="large" block @click="$router.push('/login')">
              Ya tengo cuenta
            </v-btn>
          </v-form>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import AuthService from '@/services/login'
import L from 'leaflet'

import 'leaflet/dist/leaflet.css'

// Fix íconos en bundlers (webpack) para que el marker aparezca correctamente
// (Leaflet por defecto busca los assets en rutas relativas que no existen en Vue CLI)
import markerIcon2x from 'leaflet/dist/images/marker-icon-2x.png'
import markerIcon from 'leaflet/dist/images/marker-icon.png'
import markerShadow from 'leaflet/dist/images/marker-shadow.png'

delete L.Icon.Default.prototype._getIconUrl
L.Icon.Default.mergeOptions({
  iconRetinaUrl: markerIcon2x,
  iconUrl: markerIcon,
  shadowUrl: markerShadow,
})

export default {
  name: 'RegisterView',
  data() {
    return {
      username: '',
      password: '',
      confirmPassword: '',
      latitud: -33.4489,
      longitud: -70.6482,
      showPassword: false,
      showConfirm: false,
      loading: false,
      errorMessage: '',
      usernameRules: [v => !!v || 'El usuario es obligatorio'],
      passwordRules: [
  v => !!v || 'La contraseña es obligatoria',
  v => (v && v.length >= 6) || 'Mínimo 6 caracteres',
      ],
      coordRules: [
        v => v !== null && v !== undefined && v !== '' || 'Campo obligatorio',
        v => !Number.isNaN(Number(v)) || 'Debe ser un número',
      ],

      // Leaflet
      map: null,
      marker: null,
    }
  },
  watch: {
    latitud() {
      this.syncMarkerFromFields()
    },
    longitud() {
      this.syncMarkerFromFields()
    },
  },
  mounted() {
    this.initMap()
  },
  beforeUnmount() {
    if (this.map) {
      this.map.remove()
      this.map = null
    }
  },
  computed: {
    confirmRules() {
      return [
  v => !!v || 'Repite tu contraseña',
  v => v === this.password || 'Las contraseñas no coinciden',
      ]
    },
  },
  methods: {
    initMap() {
      if (this.map || !this.$refs.mapEl) return

      const start = [Number(this.latitud), Number(this.longitud)]
      this.map = L.map(this.$refs.mapEl, {
        zoomControl: true,
      }).setView(start, 13)

      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors',
      }).addTo(this.map)

      this.marker = L.marker(start, { draggable: true }).addTo(this.map)

      this.map.on('click', (e) => {
        this.latitud = Number(e.latlng.lat.toFixed(6))
        this.longitud = Number(e.latlng.lng.toFixed(6))
      })

      this.marker.on('dragend', () => {
        const ll = this.marker.getLatLng()
        this.latitud = Number(ll.lat.toFixed(6))
        this.longitud = Number(ll.lng.toFixed(6))
      })
    },
    syncMarkerFromFields() {
      if (!this.map || !this.marker) return
      const lat = Number(this.latitud)
      const lng = Number(this.longitud)
      if (Number.isNaN(lat) || Number.isNaN(lng)) return
      this.marker.setLatLng([lat, lng])
      // no resetear zoom, solo centrar suave
      this.map.panTo([lat, lng], { animate: true })
    },
    async onSubmit() {
      const ok = await this.$refs.form?.validate?.()
      if (!ok?.valid) return

      this.loading = true
      this.errorMessage = ''
      try {
        await AuthService.register({
          username: this.username,
          password: this.password,
          latitud: Number(this.latitud),
          longitud: Number(this.longitud),
        })

        this.$router.push('/login')
      } catch (err) {
        const message =
          err?.response?.data?.message ||
          err?.response?.data?.error ||
          err?.message ||
          'No se pudo registrar.'
        this.errorMessage = message
      } finally {
        this.loading = false
      }
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

.map-wrap {
  border: 1px solid rgba(0, 0, 0, 0.12);
  border-radius: 12px;
  padding: 12px;
  background: #fff;
}

.leaflet-map {
  width: 100%;
  height: 220px;
  border-radius: 10px;
}

@media (min-width: 600px) {
  .leaflet-map {
    height: 240px;
  }
}
</style>
