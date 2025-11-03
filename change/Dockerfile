# ============================================================
#  Dockerfile pour déployer un module EJB sur WildFly 34
# ============================================================

# Image de base : WildFly 34 avec JDK 21
FROM quay.io/wildfly/wildfly:34.0.1.Final-jdk21

# Argument : chemin du fichier EJB à copier
ARG EJB_FILE=target/change-1.0.0.jar

# Copier le module EJB dans le répertoire de déploiement de WildFly
COPY ${EJB_FILE} /opt/jboss/wildfly/standalone/deployments/

# (Optionnel) Copier le driver PostgreSQL dans le répertoire de déploiement
# Décommente la ligne suivante si tu veux intégrer le driver
# COPY --from=quay.io/binaml/postgresql:16 /usr/share/java/postgresql.jar /opt/jboss/wildfly/standalone/deployments/

# Créer un utilisateur d’application (ex: pour les appels EJB)
RUN /opt/jboss/wildfly/bin/add-user.sh -a -u change -p change -g ejb -s

# Exposer les ports HTTP (8081) et de gestion (9991)
EXPOSE 8081 9991

# Lancer WildFly et le lier à toutes les interfaces
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
