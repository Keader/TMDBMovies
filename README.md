# TMDB Movies
![Preview-Screens](https://github.com/Keader/TMDBMovies/blob/master/screenshot/united.png)

### Qual a função do aplicativo?
Exibir lista de filmes populares, segundo o site [TMDB](https://www.themoviedb.org/movie?language=pt-BR).
Ao clicar em um filme, são carregados alguns detalhes sobre o mesmo.

### Decisões de Projeto
Para confecção deste aplicativo, foi decidido que a fonte da verdade (source of truth) seria o banco de dados local.
Todos os dados são pegos da API e salvos no banco de dados, para só então serem utilizados pelo aplicativo.
Foi notado que o TMDB retorna a lista de filmes em forma de páginas, com 20 elementos por página.
Sendo assim, foi utilizada a biblioteca [Paging2](https://developer.android.com/topic/libraries/architecture/paging) para carregar os dados também de forma paginada.
Objetivando assim, aumentar a eficiência no carregamento das informações.

### Sobre o App
Este aplicativo utiliza a arquitetura MVVM (Model-View-View-Model), além de bibliotecas do Android Jetpack, Material Design e de terceiros.
Dentre as bibliotecas pode-se destacar:
- [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) para navegação entre os fragmentos.
- [Retrofit](https://square.github.io/retrofit/) para chamadas a API do TMDB.
- [OkHttp](https://square.github.io/okhttp/) utilizado como cliente HTTP do Retrofit.
- [Gson](https://github.com/google/gson) para conversão dos Json retornados pela API.
- [Hilt](https://dagger.dev/hilt/) para injeção de dependências.
- [Timber](https://github.com/JakeWharton/timber) para logs do app.
- [Glide](https://github.com/bumptech/glide) para carregar imagens da internet e fazer cache delas de forma eficiente.
- [Room](https://developer.android.com/jetpack/androidx/releases/room) banco de dados local do aplicativo.
- [Paging2](https://developer.android.com/topic/libraries/architecture/paging) para carregar dados de forma paginada.
- [Lottie](https://github.com/airbnb/lottie-android) para exibir animações (em formado Lottie/Json).
- [Truth](https://truth.dev/) para realizar asserts nos testes, mais legíveis ao ser humano.

