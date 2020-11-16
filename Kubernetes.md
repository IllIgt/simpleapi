## Технологии разработки программного обеспечения

### Лабораторная работа №2: создание кластера Kubernetes и деплой приложения

**Ратников Алексей Михайлович 3МБД2001**

Целью лабораторной работы является знакомство с кластерной архитектурой на примере Kubernetes, 
а также деплоем приложения в кластер.

## Манифесты

deployment.yaml

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-deployment
spec:
  replicas: 10
  selector:
    matchLabels:
      app: my-app
  strategy:
    rollingUpdate:
      maxSurge: 1
      maxUnavailable: 1
    type: RollingUpdate
  template:
    metadata:
      labels:
        app: my-app
    spec:
      containers:
        - image: simpleapi:latest
          imagePullPolicy: Never
          name: simpleapi
          ports:
            - containerPort: 8080
      hostAliases:
        - ip: "192.168.49.1"
          hostnames:
            - postgres.local
```

service.yaml

```
apiVersion: v1
kind: Service
metadata:
  name: my-service
spec:
  type: NodePort
  ports:
    - nodePort: 31317
      port: 8080
      protocol: TCP
      targetPort: 8080
  selector:
    app: my-app
```
## Активные поды в терминале
![Image of pods](./markdown/terminal_cluster.png?raw=true)


## Kubernetes Dashboard
[**VIDEO**](https://youtu.be/vidHRsxZXVw)