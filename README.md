# 🎤 SolvoLabs - Pitch Elevator

Une application Java 23 / Spring Boot/AI pour assister l'utilisateur à mieux se présenter en entretien d'embauche (grâce à l'IA).

## ⚙️ Prérequis

- Docker
- Une clé API OpenAI (ex: `sk-...`)

## 🔧 Configuration via environnement

3 variables d’environnement sont requises :

```bash
OPEN_API_KEY=ta_cle_openai
OPEN_AI_MODEL=gpt-4o
OPEN_AI_TEMPERATURE=0.7
```

## ▶️ Lancer le projet

```bash
docker-compose up --build
```

Puis visitez [http://localhost:8080](http://localhost:8080).

## 🧪 Lancer les tests

```bash
./mvnw test
```

## Et sinon ?

Fait avec ❤️ par Mickaël Andrieu, ouvert aux suggestions mais pas aux contributions. 

**Le code de ce projet est réutilisable, sans aucune condition d'attribution.**
