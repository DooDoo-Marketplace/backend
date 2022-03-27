# Docker
Пока самые базовые настройки докера. Чтобы запустить локальную бд просто в docker-compose.yml запускаете сервис postgres.

Для того, чтобы запустить образ приложения в докере (это скорее для фронта), то в пропертях меняете url бд, выполняете команду: 
```bash
mvn clean package -DskipTests && docker build -t user-service .
```
И также в docker-compose.yml запускаете сервис.

# Liquibase
Вам нужно накатить изменения в бд для выполнения такси - создаете sql скрипт и rollback к нему.
Создаете в директории changes xml файл с назаванием ветки и комментарием (например DBEB-10-create-logs-table.xml), где добавляете changeSet с sql скриптами.
И этот файл включаем в главный changelog-master.xml. При больших изменениях ставьте тег, чтобы потом к нему вернуться если что.

Если у вас при локальной разработке приложение падает с "какой-то ошибкой чек сумм", значит вы скорее всего изменили накатанные скрипты, так делать не нужно! Создавайте новые скрипты и добавляйте их, как указано выше. 
Если что крайний фикс - снести локальные таблицы и накатить скрипты заново.

Примеры роллбеков:
1. По тегу: mvn liquibase:rollback -Dliquibase.rollbackTag=\<tag name>
2. Откатиться на n версий назад: mvn liquibase:rollback -Dliquibase.rollbackCount=\<n>
3. По дате: mvn liquibase:rollback "-Dliquibase.rollbackDate=Jun 22, 2021"

В интернете можно найти еще много примеров. Важно, что в локальной бд в докере роллбеки не работают, это норма, и там это особо не нужно. В теории можно настроить бд в докере, чтобы так сделать было можно, но я не стал тратить на это время.

# Git
Две основные ветки main и dev. В main последняя релизнутая версия, в dev - последняя версия разработки.
Для текущей задачи создаете свою ветку от dev с названием как в Jir'е (DBEB-"N"), делаете таску, пушите ее и создаете пулл реквест.

Перед мержем пулл реквеста сквоште коммиты, это можно делать в самом гитхабе, на кнопке merge pull request можно выбрать вариацию squash and merge.

Свои коммиты называйте в формате: DBEB-"N" "comment". Например DBEB-10 bug fixed

### Как подтянуть изменения из dev'a к себе:
Если у вас несколько коммитов то засквошьте их, туториал ниже. Если вы этого не сделаете, то решение конфликтов с чужими изменениями не будет возможным.

После этого вам нужно спулить dev к себе, и сделать rebase

Из вашей ветки это будет выглядеть примерно вот так:
```bash
git rebase -i HEAD~...
git pull origin dev
git rebase dev
```
Работа не с dev, а другой веткой делается аналогично 
### Как сквошить коммиты:
Пишите git rebase -i HEAD~n (n - количество коммитов)

Дальше у вас откроется вим, в котором первый pick оставляете, а в остальных меняете pick на s.

И потом выходите (esc -> :wq!) (В терминале идеи на esc будет уходить таргет с терминала, просто вернитесь на терминал и пишите :wq!)

Потом останется только выбрать комментарий к общему коммиту и опять выйти

### 

