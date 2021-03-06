<h1 align="center">
    TopK
</h1>

<h2 align="center">
    Lista de repositórios Kotlin com mais estrelas
</h2>

<p align="center">
  <img src="screenshots/frag_repo_list.png" width="270" alt="Lista de repositórios">
  <span>&nbsp;&nbsp;&nbsp;</span>
  <img src="screenshots/frag_repo_detail.png" width="270" alt="Detalhe de um repositório">
</p>

<p>&nbsp;&nbsp;&nbsp;</p>

<p align="center">
  <img src="screenshots/frag_repo_list_land.png" height="270" alt="Lista do filme - retrato">
</p>


## Arquitetura

- [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)

## Features

- Lista de repositórios Kotlin com mais estrelas
- Detalhes de um repositório
- Direcionamento para a página no Github
- Paginação da lista de repositórios
- Persistência local da lista 
- Acesso ao conteúdo mesmo offline


## Libs

- [Kotlin](https://kotlinlang.org/)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [Navigation](https://developer.android.com/guide/navigation)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [DataBinding](https://developer.android.com/topic/libraries/data-binding)
- [Retrofit2](https://square.github.io/retrofit/)
- [Room](https://developer.android.com/topic/libraries/architecture/room)
- [Coroutines](https://developer.android.com/kotlin/coroutines)
- [Glide](https://bumptech.github.io/glide/)
- [Koin](https://insert-koin.io/)

## API

- [Github REST API v3](https://developer.github.com/v3/)


### Pré-requisitos

- <p>Android 5.1 ou superior</p>


## Configurar seu ambiente de teste

Este projeto foi desenvolvido usando Android Studio 4.0.1.

- <p>Testes de instrumentação</p>

Para evitar instabilidades, é altamente recomendável que você desative as animações do sistema nos 
dispositivos virtuais ou físicos usados para testes.
No dispositivo, em Config. > Opções do desenvolvedor, desative estas três configurações:

-Escala de animação da janela
-Escala de animação de transição
-Escala de duração do Animator


- <p>Testes de unidade</p>

Para executar os testes unitários utilize:

```

./gradlew testDebugUnitTest


```

Windows:

```

gradlew.bat testDebugUnitTest


```
