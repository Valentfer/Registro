La aplicación de registro de asistencia en Android realiza tres funciones principales:

1. **Autenticación de usuarios**: Cuando un usuario inicia la aplicación, se le presenta una pantalla de inicio de sesión donde puede ingresar su DNI. Este DNI se utiliza para autenticar al usuario y verificar si tiene permiso para usar la aplicación.

2. **Registro de entrada y salida**: Una vez que un usuario está autenticado, puede registrar su entrada y salida en la aplicación. Cuando el usuario marca su entrada o salida, la aplicación envía una solicitud POST a un servidor con la hora actual y la fecha. Estos datos se almacenan en una base de datos en el servidor.

3. **Visualización de datos de asistencia**: Los usuarios pueden ver sus datos de asistencia en la aplicación. Los datos incluyen la fecha, la hora de entrada y la hora de salida. Estos datos se muestran en un formato fácil de entender y están organizados cronológicamente para facilitar la revisión.

Además, la aplicación utiliza la biblioteca Volley para realizar solicitudes HTTP. Volley es una biblioteca de Android que facilita la implementación de solicitudes de red y el manejo de respuestas de manera eficiente.

Por último, la aplicación utiliza SharedPreferences para almacenar datos localmente en el dispositivo del usuario. Esto incluye información como el estado de un botón y los datos del usuario después de iniciar sesión.

En resumen, esta aplicación de registro de asistencia permite a los usuarios registrar su entrada y salida de manera eficiente y segura, y proporciona una forma conveniente de revisar sus datos de asistencia.
