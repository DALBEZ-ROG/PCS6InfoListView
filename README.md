# PCS6 Info ListView

Aplicación Android nativa en Kotlin que consulta una base de datos en **Supabase** y muestra el listado de alumnos en un `ListView` con tema oscuro moderno.

## Capturas de pantalla

> _Agregar capturas de la app corriendo en un dispositivo o emulador._

## Características

- Consulta en tiempo real a una tabla de Supabase usando **postgrest-kt**
- Lista de alumnos con nombre, correo institucional y cédula
- Avatar generado automáticamente via **Gravatar** (MD5 del correo) con recorte circular usando **Glide**
- Tema completamente oscuro (`#000000`) con acentos en azul claro
- Efecto ripple en cada fila al tocar
- `BuildConfig` para inyectar credenciales desde `local.properties` sin exponerlas en el código

## Tecnologías

| Librería | Versión | Uso |
|---|---|---|
| [Supabase Kotlin SDK](https://github.com/supabase-community/supabase-kt) | BOM 3.1.4 | Cliente Supabase + Postgrest |
| [Ktor Client Android](https://ktor.io/) | 3.1.3 | Motor HTTP para Supabase |
| [Glide](https://bumptech.github.io/glide/) | 4.16.0 | Carga y transformación de imágenes |
| Kotlin Serialization | 2.0.21 | Deserialización de respuestas JSON |
| AndroidX AppCompat / ConstraintLayout | — | UI base |

## Estructura del proyecto

```
app/src/main/
├── java/com/uteq/pcs6infolistview/
│   ├── Alumno.kt            # Data class serializable (modelo)
│   ├── SupabaseManager.kt   # Singleton del cliente Supabase
│   ├── AlumnoAdapter.kt     # BaseAdapter con ViewHolder + Gravatar
│   └── MainActivity.kt      # Actividad principal + coroutine query
└── res/
    ├── layout/
    │   ├── activity_main.xml  # ListView principal (tema oscuro)
    │   └── item_alumno.xml    # Fila del ListView
    └── drawable/
        ├── avatar_border.xml  # Borde circular blanco para avatar
        ├── title_background.xml # Fondo degradado del título
        └── item_ripple.xml    # Efecto ripple oscuro por fila
```

## Esquema de la tabla en Supabase

```sql
create table "Alumnos" (
  id                    bigint primary key,
  cedula                varchar,
  apellidos_nombres     text,
  correo_institucional  text,
  correo_microsoft      text
);
```

## Configuración y uso

### 1. Clonar el repositorio

```bash
git clone https://github.com/DALBEZ-ROG/PCS6InfoListView.git
cd PCS6InfoListView
```

### 2. Configurar credenciales

Copia el archivo de ejemplo y rellena tus valores:

```bash
cp local.properties.example local.properties
```

Edita `local.properties` y reemplaza los valores:

```properties
SUPABASE_URL=https://<tu-proyecto>.supabase.co
SUPABASE_KEY=sb_publishable_<tu-clave-publicable>
```

> **Importante:** usa la **Clave publicable** (`sb_publishable_...`) que aparece en
> **Supabase → Project Settings → API → Project API keys**.
> Nunca uses la _service role key_ (`sb_secret_...`) en una app cliente: tiene
> privilegios de administrador y omite Row Level Security.

> **Nunca subas `local.properties` al repositorio.** Ya está en `.gitignore`.

### 3. Sincronizar Gradle y ejecutar

Abre el proyecto en **Android Studio**, haz clic en **Sync Project with Gradle Files** y ejecuta la app en un dispositivo o emulador con API 24+.

## Seguridad

Las credenciales (`SUPABASE_URL` y `SUPABASE_KEY`) se leen desde `local.properties` en tiempo de compilación y se exponen como constantes `BuildConfig.SUPABASE_URL` / `BuildConfig.SUPABASE_KEY`. El archivo `local.properties` está excluido del control de versiones mediante `.gitignore`.

Se recomienda:
- Usar la **clave publicable** (`sb_publishable_...`) en lugar de la service role key en apps cliente.
- Configurar **Row Level Security (RLS)** en Supabase para controlar el acceso a los datos. Sin RLS activo, cualquier persona que obtenga la clave publicable puede leer toda la tabla.

## Requisitos

- Android Studio Hedgehog o superior
- Android SDK API 24+ (minSdk)
- Cuenta en [Supabase](https://supabase.com) con la tabla `Alumnos` creada

## Licencia

MIT
