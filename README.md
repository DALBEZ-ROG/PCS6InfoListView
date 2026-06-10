# PCS6 Info ListView

Aplicación Android nativa en Kotlin que consulta la tabla **alumnos** en **Supabase** y presenta la información en múltiples pantallas con navegación inferior, tema completamente oscuro y dropdowns de filtro.

---

## Capturas de pantalla

<img width="738" height="1600" alt="image" src="https://github.com/user-attachments/assets/7899854e-5531-4ea2-86f2-f7f157a4c6a4" />
<img width="738" height="1600" alt="image" src="https://github.com/user-attachments/assets/bdf07636-90c0-456a-81ff-2527fdaf429a" />
<img width="738" height="1600" alt="image" src="https://github.com/user-attachments/assets/02baa714-2c65-4ee2-8aec-6ba22a3c2166" />

## Características

- Tres pantallas con **BottomNavigationView** compartida: Alumnos, Materias, Nombres
- **Pantalla Alumnos:** dos `ExposedDropdownMenu` (Semestre y Materia) + lista de alumnos cargada desde Supabase ordenada por nombre
- **Pantalla Materias:** lista estática de asignaturas de Sexto Semestre PCS
- **Pantalla Nombres:** lista dinámica de nombres consultados desde Supabase en orden alfabético
- Avatares cargados desde la URL de Storage de Supabase con **Glide** + `CircleCrop`; placeholder vectorial `ic_person` si no hay foto
- Íconos descriptivos en cada fila: correo (`ic_dialog_email`) y teléfono (`ic_menu_call`) tintados en `#64B5F6`
- Manejo de errores con `MaterialAlertDialogBuilder` via `SupabaseErrorHandler`
- Tema completamente oscuro (`#000000`) con acentos en azul claro (`#64B5F6`)
- Efecto ripple en cada fila al tocar
- Credenciales inyectadas desde `local.properties` como `BuildConfig` — nunca en el código fuente

---

## Tecnologías

|                              Librería                                                |                                 Uso                            |
|--------------------------------------------------------------------------------------|----------------------------------------------------------------|
| [Supabase Kotlin SDK](https://github.com/supabase-community/supabase-kt) (BOM 3.1.4) | Cliente Supabase + Postgrest                                   |
| [Ktor Client Android](https://ktor.io/) 3.1.3                                        | Motor HTTP para Supabase                                       |
| [Glide](https://bumptech.github.io/glide/) 4.16.0                                    | Carga de imágenes + CircleCrop                                 |
| [Material Components](https://m3.material.io/)                                       | BottomNavigationView, ExposedDropdownMenu, MaterialAlertDialog |
| Kotlin Serialization 2.0.21                                                          | Deserialización de respuestas JSON                             |
| AndroidX AppCompat / Lifecycle                                                       | Base de actividades + `lifecycleScope`                         |

---

## Estructura del proyecto

```
app/src/main/
├── java/com/uteq/pcs6infolistview/
│   ├── Alumno.kt                  # Data class @Serializable — campos sin @SerialName (nombres coinciden con columnas)
│   ├── SupabaseManager.kt         # Singleton del cliente Supabase
│   ├── AlumnoAdapter.kt           # ArrayAdapter con Glide + foto Supabase
│   ├── MainActivity.kt            # Pantalla Alumnos + dropdowns + BottomNav
│   ├── MateriasActivity.kt        # Pantalla Materias + BottomNav
│   ├── NombresActivity.kt         # Pantalla Nombres (query Supabase) + BottomNav
│   └── utils/
│       └── SupabaseErrorHandler.kt # Diálogo de error Material
└── res/
    ├── layout/
    │   ├── activity_main.xml      # Logo + dropdowns + ListView + BottomNav
    │   ├── activity_materias.xml  # Logo + título + ListView + BottomNav
    │   ├── activity_nombres.xml   # Logo + título + ListView + BottomNav
    │   └── item_alumno.xml        # Fila: avatar circular + nombre + ícono+correo + ícono+teléfono + paralelo
    ├── menu/
    │   └── bottom_nav_menu.xml    # 3 ítems de navegación inferior
    ├── color/
    │   └── selector_nav.xml       # Azul activo / gris inactivo para BottomNav
    ├── values/
    │   └── strings.xml            # string-array: semestres, materias_sexto
    └── drawable/
        ├── ic_person.xml          # Vector placeholder de persona
        ├── avatar_border.xml      # Borde circular para avatar
        ├── item_ripple.xml        # Efecto ripple oscuro por fila
        ├── logo_uteq.png          # Logo institucional UTEQ
        └── title_background.xml   # Fondo degradado (legado)
```

---

## Esquema de la tabla en Supabase

```sql
create table alumnos (
  id          bigint primary key,   -- mapeado a Int en Alumno.kt
  created_at  timestamptz default now(),
  nombres     text,
  correo      text,
  telefono    text,
  foto        text,          -- ruta relativa en Supabase Storage
  paralelo    char
);
```

> La columna `foto` almacena la **ruta relativa** dentro de Supabase Storage.  
> La app construye la URL completa así:  
> `"https://<proyecto>.supabase.co" + alumno.foto`

---

## Configuración y uso

### 1. Clonar el repositorio

```bash
git clone https://github.com/DALBEZ-ROG/PCS6InfoListView.git
cd PCS6InfoListView
```

### 2. Configurar credenciales

Edita `local.properties` (no se sube al repo) y agrega:

```properties
SUPABASE_URL=https://<tu-proyecto>.supabase.co
SUPABASE_KEY=sb_publishable_<tu-clave-publicable>
```

> Usa la **Clave publicable** (`sb_publishable_...`) de **Supabase → Project Settings → API**.  
> Nunca uses la _service role key_ — tiene privilegios de administrador y omite RLS.

### 3. Sincronizar y ejecutar

Abre en **Android Studio**, haz **Sync Project with Gradle Files** y ejecuta en un dispositivo o emulador con **API 24+**.

---

## Seguridad

- Las credenciales se leen desde `local.properties` en compilación como `BuildConfig.SUPABASE_URL` / `BuildConfig.SUPABASE_KEY`.
- `local.properties` está en `.gitignore` — nunca se sube al repositorio.
- Se usa la clave publicable, no la service role key.
- Se recomienda tener **Row Level Security (RLS)** activo en la tabla `alumnos`.

---

## Requisitos

- Android Studio Hedgehog o superior
- Android SDK **API 24+** (minSdk)
- Cuenta en [Supabase](https://supabase.com) con la tabla `alumnos` creada y RLS configurado

---

## Licencia

MIT
