# Exemplos usando cURL

- `GET /users/{username}` obtém os detalhes de um usuário

```sh
curl -i http://localhost:8080/users/alex
```

```txt
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 02 May 2021 21:42:38 GMT

{"username":"alex","email":"alex@email.com"}
```

- `POST /users` adiciona um novo usuário

```sh
curl -i -X POST -H 'Content-type: application/json' -d '{"username": "gabriel", "email": "gabriel@email.com"}' http://localhost:8080/users
```

```txt
HTTP/1.1 201
Location: /users/gabriel
Content-Length: 0
Date: Sun, 02 May 2021 21:46:03 GMT
```

- `GET /courses` lista os cursos já cadastrado

```sh
curl -i http://localhost:8080/courses
```

```txt
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 02 May 2021 21:49:16 GMT

[
    {"code":"java-1","name":"Java OO","shortDescription":"Java and O..."},
    {"code":"java-2","name":"Java Collections","shortDescription":"Java Colle..."}
]
```

- `GET /courses/{code}` obtém os detalhes de um curso

```sh
curl -i http://localhost:8080/courses/java-1
```

```txt
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 02 May 2021 21:51:59 GMT

{"code":"java-1","name":"Java OO","shortDescription":"Java and O..."}
```

- `POST /courses` adiciona um novo curso

```sh
curl -i -X POST -H 'Content-type: application/json' -d '{"code": "spring-1", "name": "Spring Basic", "description": "Spring Core, Spring MVC and more."}' http://localhost:8080/courses
```

```txt
HTTP/1.1 201
Location: /courses/spring-1
Content-Length: 0
Date: Sun, 02 May 2021 21:54:14 GMT
```

- `POST /courses/{courseCode}/sections` adiciona uma nova aula à um curso

```sh
curl -i -X POST -H 'Content-type: application/json' -d '{"code": "flutter-cores-dinamicas", "title": "Flutter: Configurando cores dinâmicas": "alex"}' http://localhost:8080/courses/{courseCode}/sections
```

```txt
HTTP/1.1 201
Location: /courses/java-2/sections/flutter-cores-dinamicas
Content-Length: 0
Date: Tue, 27 Sep 2022 20:57:59 GMT
```

- `POST /courses/{courseCode}/sections/{sectionCode}` adiciona um novo vídeo à uma aula

```sh
curl -i -X POST -H 'Content-type: application/json' -d '{"video": "https://www.youtube.com/watch?v=gI4-vj0WpKM"}' http://localhost:8080/courses/{courseCode}/sections/{sectionCode}
```

```txt
HTTP/1.1 201
Location: /video/flutter-cores-dinamicas
Content-Length: 0
Date: Tue, 27 Sep 2022 20:57:59 GMT
```

- `POST /courses/{courseCode}/enroll` aplica uma nova matrícula em um curso

```sh
curl -i -X POST -H 'Content-type: application/json' -d '{"username": "alex"}' http://localhost:8080/courses/{courseCode}/enroll
```


- `GET /sectionByVideosReport` lista a quantidade de vídeos por aula em cursos com matriculados

```sh
curl -i http://localhost:8080/sectionByVideosReport
```

```txt
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 27 Sep 2022 20:57:59 GMT