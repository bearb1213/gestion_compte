<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Erreur - Bank Courant</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%);
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            color: #333;
        }

        .error-container {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.2);
            width: 90%;
            max-width: 800px;
            margin: 20px;
        }

        .error-header {
            text-align: center;
            margin-bottom: 30px;
            border-bottom: 2px solid #ff6b6b;
            padding-bottom: 20px;
        }

        .error-header h1 {
            color: #ff6b6b;
            margin-bottom: 10px;
            font-size: 2.5em;
        }

        .error-header p {
            color: #666;
            font-size: 1.2em;
            margin: 0;
        }

        .error-card {
            background: #fff5f5;
            border: 1px solid #ffcccc;
            border-radius: 10px;
            padding: 25px;
            margin-bottom: 30px;
        }

        .error-card h2 {
            color: #d63031;
            margin-top: 0;
            border-bottom: 1px solid #ffcccc;
            padding-bottom: 10px;
        }

        .error-message {
            background: #ffeaa7;
            border: 1px solid #fdcb6e;
            border-radius: 5px;
            padding: 15px;
            margin-bottom: 15px;
            font-weight: bold;
        }

        .error-info {
            background: #dfe6e9;
            border: 1px solid #b2bec3;
            border-radius: 5px;
            padding: 12px;
            margin-bottom: 10px;
        }

        .stack-trace {
            background: #2d3436;
            color: #dfe6e9;
            border-radius: 5px;
            padding: 15px;
            margin-top: 20px;
            overflow-x: auto;
        }

        .stack-trace h3 {
            color: #74b9ff;
            margin-top: 0;
        }

        .stack-trace pre {
            white-space: pre-wrap;
            font-family: 'Courier New', monospace;
            font-size: 12px;
            margin: 0;
        }

        .error-actions {
            display: flex;
            justify-content: center;
            gap: 15px;
            flex-wrap: wrap;
            margin-bottom: 30px;
        }

        .btn {
            display: inline-block;
            padding: 12px 25px;
            text-decoration: none;
            border-radius: 25px;
            font-weight: bold;
            transition: all 0.3s ease;
            text-align: center;
            min-width: 120px;
        }

        .btn-back {
            background: #74b9ff;
            color: white;
            border: 2px solid #74b9ff;
        }

        .btn-back:hover {
            background: #0984e3;
            border-color: #0984e3;
            transform: translateY(-2px);
        }

        .btn-home {
            background: #00b894;
            color: white;
            border: 2px solid #00b894;
        }

        .btn-home:hover {
            background: #00a085;
            border-color: #00a085;
            transform: translateY(-2px);
        }

        .btn-login {
            background: #a29bfe;
            color: white;
            border: 2px solid #a29bfe;
        }

        .btn-login:hover {
            background: #6c5ce7;
            border-color: #6c5ce7;
            transform: translateY(-2px);
        }

        .error-footer {
            text-align: center;
            color: #666;
            font-size: 14px;
            border-top: 1px solid #ddd;
            padding-top: 20px;
        }

        .error-footer p {
            margin: 5px 0;
        }

        /* Responsive */
        @media (max-width: 600px) {
            .error-container {
                padding: 20px;
                margin: 10px;
            }
            
            .error-actions {
                flex-direction: column;
                align-items: center;
            }
            
            .btn {
                width: 100%;
                max-width: 200px;
            }
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-header">
            <h1>❌ Erreur</h1>
            <p>Une erreur s'est produite lors du traitement de votre demande</p>
        </div>
        
        <div class="error-content">
            <div class="error-card">
                <h2>Détails de l'erreur</h2>
                
                <c:if test="${not empty error}">
                    <div class="error-message">
                        <strong>Message:</strong> ${error}
                    </div>
                </c:if>
                
                <c:if test="${not empty requestScope['jakarta.servlet.error.message']}">
                    <div class="error-message">
                        <strong>Message d'erreur:</strong> ${requestScope['jakarta.servlet.error.message']}
                    </div>
                </c:if>
                
                <c:if test="${not empty requestScope['jakarta.servlet.error.status_code']}">
                    <div class="error-info">
                        <strong>Code d'erreur:</strong> ${requestScope['jakarta.servlet.error.status_code']}
                    </div>
                </c:if>
                
                <c:if test="${not empty requestScope['jakarta.servlet.error.servlet_name']}">
                    <div class="error-info">
                        <strong>Servlet:</strong> ${requestScope['jakarta.servlet.error.servlet_name']}
                    </div>
                </c:if>
                
                <c:if test="${not empty requestScope['jakarta.servlet.error.request_uri']}">
                    <div class="error-info">
                        <strong>URI:</strong> ${requestScope['jakarta.servlet.error.request_uri']}
                    </div>
                </c:if>
                
                <!-- Affichage de la stack trace en mode développement -->
                <c:if test="${pageContext.exception != null}">
                    <div class="stack-trace">
                        <h3>Stack Trace:</h3>
                        <pre>${pageContext.exception.stackTrace}</pre>
                    </div>
                </c:if>
            </div>
            
            <div class="error-actions">
                <a href="javascript:history.back()" class="btn btn-back">← Retour</a>
                <a href="${pageContext.request.contextPath}/accueil" class="btn btn-home">🏠 Accueil</a>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-login">🔐 Se reconnecter</a>
            </div>
        </div>
        
        <div class="error-footer">
            <p>Si le problème persiste, veuillez contacter l'administrateur système.</p>
            <p>&copy; 2024 Bank Courant. Tous droits réservés.</p>
        </div>
    </div>
</body>
</html>