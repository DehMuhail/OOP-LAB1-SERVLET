# Взяти готовий Tomcat
FROM tomcat:10.1-jdk17

# Очистити стандартні апки
RUN rm -rf /usr/local/tomcat/webapps/*

# Копіюємо твій WAR файл
COPY target/mm.war /usr/local/tomcat/webapps/ROOT.war

# Відкрити порт 8080
EXPOSE 8080

# Запустити Tomcat
CMD ["catalina.sh", "run"]
