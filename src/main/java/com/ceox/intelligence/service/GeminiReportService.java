package com.ceox.intelligence.service;

import com.ceox.intelligence.model.WeeklyDataDTO;
import com.ceox.intelligence.model.WeeklyReportResponse;

import java.time.LocalDate;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class GeminiReportService {

  private final ChatClient chatClient;

  public GeminiReportService(ChatClient chatClient) {
    this.chatClient = chatClient;
  }

  public WeeklyReportResponse generateFullReport(WeeklyDataDTO data) {
    String html = generateWeeklyReport(data);
    String markdown = convertHtmlToMarkdown(html);
    return new WeeklyReportResponse(html, markdown);
  }

  private String convertHtmlToMarkdown(String html) {
    String prompt = """
        Convierte el siguiente contenido HTML en un archivo Markdown estructurado, usando títulos, subtítulos y listas anidadas.
        El formato debe ser compatible con Markmap.js (https://markmap.js.org).
        Usa solo títulos con #, ##, ### y listas con -, sin etiquetas HTML ni bloques de código.

        HTML:
        %s
        """
        .formatted(html);

    return chatClient.prompt()
        .user(prompt)
        .call()
        .content();
  }

  public String generateWeeklyReport(WeeklyDataDTO data) {
    String weekRange = "del " + LocalDate.now().minusDays(7) + " al " + LocalDate.now().minusDays(1);

    //Este prompt no está completo debido a que nos reservamos parte de nuestra interacción con Gemini para uso interno y privado.
    String prompt = """
                        Eres ...
                        Tu misión es generar un <strong>reporte profesional y estratégico semanal</strong> en formato HTML, basado en los datos entregados.

                        📅 El reporte debe analizar exclusivamente lo ocurrido durante la semana <strong>%s</strong>.

                        🎯 Estructura obligatoria del contenido:

                        1. <strong>Trabajo realizado y eventos</strong>
                            - Subtítulo: <strong>Tareas completadas</strong>
                              - Lista detallada de todas las tareas finalizadas esa semana, incluyendo fecha, responsable y proyecto.
                            - Subtítulo: <strong>Eventos relevantes</strong>
                              - Lista detallada de todos los eventos extraídos de las notificaciones cuya fecha esté **dentro de los 7 días indicados**. No omitas ninguno. Pueden ser reuniones, nuevos clientes, avisos importantes, etc.

                        2. <strong>Análisis actual de Maen Studios</strong>
                            - Subsecciones:
                              - Clientes actuales
                              - Proyectos activos por cliente (con porcentaje de progreso)
                              - Análisis y oportunidades por cliente
                              - Oportunidades de mejora generales
                              - Ideas y propuestas estratégicas

                        3. <strong>Prioridades y enfoque</strong>
                            - Subsecciones:
                              - Proyectos con deadlines más cercanos
                              - Tareas a priorizar
                              - Enfoque próximos días
                              - Nota final (conclusión estratégica)

                        🧠 Instrucciones clave:
                        - Usa solo estas etiquetas: <html>, <head>, <style>, <body>, <h1>, <h2>, <h3>, <p>, <ul>, <li>, <strong>, <img>. Nada más.
                        - Redacta contenido extenso, analítico, profesional. Usa párrafos y listas bien separadas.
                        - Sigue estrictamente el orden y las subsecciones dadas.
                        - Asegúrate de incluir <strong>todas</strong> las notificaciones de la semana si su fecha está dentro del rango indicado.
                        - Incluye en el <head> un bloque <style> con los estilos ya definidos.

                        ❗ Devuelve exclusivamente el HTML como documento válido. No incluyas ningún texto, espacio, salto de línea o carácter fuera de las etiquetas <html> y </html>. No uses comillas, ni Markdown, ni bloques de código. Solo el documento HTML limpio, sin comentarios ni explicaciones.

        <html>
          <head>
            <style>
              body { font-family: Arial, sans-serif; line-height: 1.6; padding: 40px; }
              h1 { font-size: 26px; margin-bottom: 10px; }
              h2 { font-size: 20px; margin-top: 30px; }
              h3 { font-size: 16px; margin-top: 20px; }
              p  { font-size: 14px; margin-bottom: 10px; }
              ul { margin-left: 20px; }
              li { margin-bottom: 6px; }
        .logo {
          position: absolute;
          top: 0px;
          right: 0px;
          max-width: 120px;
        }
        </style>
          </head>
          <body>
        <img src="https://res.cloudinary.com/dk4wtixta/image/upload/v1750280911/ceox-logo-reportes_ac71d3.png" class="logo" />

            <p>Semana analizada: 2025-06-11 al 2025-06-17</p>


                            <h2>1. Trabajo realizado y eventos</h2>
                            <h3>Tareas completadas</h3>
                            <ul>
                              <li>2025-06-16 09:24: Álvaro García completó la tarea 'Storyboard' (Campaña Navidad).</li>
                              <li>2025-06-16 12:09: Jose Martín completó 'Vídeo Tráiler' (Web iPhone 17).</li>
                            </ul>

                            <h3>Eventos relevantes</h3>
                            <ul>
                              <li>2025-06-16 07:00: Reunión de equipo registrada.</li>
                              <li>2025-06-16 12:18: Se añadió un nuevo cliente: Nike.</li>
                            </ul>

                            <!-- ... resto de secciones ... -->
                          </body>
                        </html>

                        --- DATOS DISPONIBLES ---

                        Tareas:
                        %s

                        Proyectos:
                        %s

                        Clientes:
                        %s

                        Notificaciones:
                        %s
                        """


                        //Más datos privados
        .formatted(
            weekRange,
            data.getTasks(),
            data.getProjects(),
            data.getClients(),
            data.getNotifications());

    return chatClient.prompt()
        .user(prompt)
        .call()
        .content();
  }

}
