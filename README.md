# Docker
Пока самые базовые настройки докера. Чтобы запустить локальную бд просто в docker-compose.yml запускаете сервис postgres.

Для того, чтобы запустить образ приложения в докере (это скорее для фронта), то в пропертях меняете url бд, выполняете команду: 
```bash
mvn clean package -DskipTests && docker build -t user-service .
```
И также в docker-compose.yml запускаете сервис.

#Liquibase
Вам нужно накатить изменения в бд для выполнения такси - создаете sql скрипт и rollback к нему.
Создаете в директории changes xml файл (название которого совпадает с названием таски!), где добавляете changeSet с sql скриптами.
И этот файл включаем в главный changelog-master.xml. При больших изменениях ставьте тег, чтобы потом к нему вернуться если что.

Если у вас при локальной разработке приложение падает с "какой-то ошибкой чек сумм", значит вы скорее всего изменили накатанные скрипты, так делать не нужно! Создавайте новые скрипты и добавляйте их, как указано выше. 
Если что крайний фикс - снести локальные таблицы и накатить скрипты заново.

Примеры роллбеков:
1. По тегу: mvn liquibase:rollback -Dliquibase.rollbackTag=\<tag name>
2. Откатиться на n версий назад: mvn liquibase:rollback -Dliquibase.rollbackCount=\<n>
3. По дате: mvn liquibase:rollback "-Dliquibase.rollbackDate=Jun 22, 2021"

В интернете можно найти еще много примеров. Важно, что в локальной бд в докере роллбеки не работают, это норма, и там это особо не нужно. В теории можно настроить бд в докере, чтобы так сделать было можно, но я не стал тратить на это время.