**Software version:** 0.0.3-SNAPSHOT

**Autor:** Jônatas Ribeiro Tonholo

---

## Desafio Evoluum

### 1. DESAFIO
O desafio consiste em criar um microservice que consulte 2 APIs externas, gerar um
CSV e um JSON e fazer o download.

a. Deve ser usado a API para consultar os estados do Brasil:
https://servicodados.ibge.gov.br/api/v1/localidades/estados

b. Deve ser usado a API para consultar as cidades:
https://servicodados.ibge.gov.br/api/v1/localidades/estados/{UF}/municipios

c. A documentação completa das APIs está no site:
https://servicodados.ibge.gov.br/api/docs/localidades

d. Os campos do CSV/JSON deverá ser:

> i. idEstado
>
> ii. siglaEstado
>
> iii. regiaoNome
>
> iv. nomeCidade
>
> v. nomeMesorregiao
>
> vi. nomeFormatado {cidade/UF}

### 2. REQUISITOS
Abaixo seguem os requisitos da biblioteca a ser desenvolvida.

a. NO CSV, a primeira linha (cabeçalho) deve conter o nome de cada campo e
a(s) linha(s) subsequente(s) deve(m) conter os valores resultante da consulta
a API.

b. Deverá ter um endpoint que retorna um json com todos os dados.

c. Deverá ter um endpoint que retorna um CSV com todos os dados.

d. Deverá ter um endpoint que envia um parâmetro, **nomeCidade**, e retorna
somente o ID da cidade.

e. Usar um cache no item (d), para que quando o nome de uma cidade for
enviado mais de uma vez, evite a chamada do serviço externo.

f. No endpoint do CSV deverá retornar um objeto do tipo **java.io.OutputStream**
como saída da transformação.

---

## Utilização das APIs desenvolvidas

Os Endpoints estão mapeados com um endereço padrão. Por exemplo http://localhost:8080/localidades/

As chamadas de cada Endpoint foram desenvolvidas realizando ou não cache, dependendo de como a API é chamada.
Qualquer Endpoint chamado com **localidades/cache/*** realizará as consultas às APIs do IBGE apenas na primeira chamada, e nas demais, buscará no cache.

>**Obs.: Foi implementado um Schedule no microservice, de modo que de hora em hora todo o cache será limpo.**

---
### Chamada de cada Endpoint desenvolvido
#### 1. Endpoint que retorna o JSON com todos os dados:
> - Sem cache: localidades/json
>
> - Com cache **(bônus)**: localidades/cache/json 

#### 2. Endpoint que retorna o CSV com todos os dados:
> - Sem cache: localidades/csv
>
> - Com cache **(bônus)**: localidades/cache/csv

#### 3. Endpoint que envia o nome da cidade retorna o id:
> - Sem cache **(bônus)**: localidades/id/{nomeCidade}
>
> - Com cache: localidades/cache/id/{nomeCidade}
>
>> Obs.: o nome da cidade não é case sensitive e pode ou não ser enviado com acentuação.
>>
>> Ex: Ambas as chamadas retornaram o id correto da cidade Nova Viçosa - BA:
>>
>>> **localidades/cache/id/nova vicosa**
>>>
>>>ou 
>>>
>>> **localidades/cache/id/NoVa viçosa**

#### 4. **(bônus)** Endpoint que limpa a Cache:
> - Com cache: localidades/cache/clear

---
## Diagrama de classes
> https://bitbucket.org/jonatas_tonholo/microservices-localidade/src/master/src/main/java/com/microservices/localidades/docs/class_diagram.png
![](https://bitbucket.org/jonatas_tonholo/microservices-localidade/src/master/src/main/java/com/microservices/localidades/docs/class_diagram.png)
