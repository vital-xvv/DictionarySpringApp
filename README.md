# DictionarySpringApp
Dictionary app with translation

**Лабораторна робота №2. Контрольні запитання.**


1.Поясніть значення наступних термінів:

- Inversion of Control (IoC);
Інверсія контролю (IoC) — це принцип проектування. IoC означає передачу керування об’єктами та їхніми залежностями від основної програми до контейнера або фреймворку. IoC — це принцип, а не шаблон проектування — деталі реалізації залежать від розробника.
- IoC container;
IoC Container— це структура для автоматичного впровадження залежностей. Він керує створенням об’єкта та його життєвим часом, а також додає залежності до класу.
- Dependency Injection (DI);
Впровадження залежностей (DI) — це шаблон проектування, який використовується для реалізації IoC. Це дозволяє створювати залежні об’єкти поза класом і надавати ці об’єкти класу різними способами. Використовуючи DI, ми переносимо створення та прив’язування залежних об’єктів за межі класу, який залежить від них.
- Dependency inversion principle.
Принцип інверсії залежностей (DIP) стверджує, що модулі високого рівня не повинні залежати від модулів низького рівня; обидва повинні залежати від абстракцій. Абстракції не повинні залежати від деталей.


2.В чому полягає різниця між анотаціями @Component та @Bean? Опишіть переваги та недоліки створення бінів за допомогою цих анотацій.

@Component призначений для автоматичного пошуку та автоконфігурації. @Bean, натомість використовується для явного зазначення та створення об'єкта.
Перший є анотацією класу, другий анотацією метода, при цьому має бути використаний @Configuration для класу. @Bean – дає більшу ступінь свободи, об'єкт можна задати за динамічної умови, або створити об'єкт бібліотеки поза Spring IoC чи від якої ми не маємо сирцевого коду, в інших випадках перший варіант простіше.


3.Чому слід уникати ін’єкцій залежностей напряму у поле біна?

В цього типу інєкції є багато проблем: по-перше, можна ін’єктити залежності в final поля, що дозволяє змінювати такі поля, а це відходить від загальноприйнятих конвенцій у мові Java. Так само такі біни важко тестувати, або змінити DI контейнер. При ін’єкції в поле клас втрачає контроль над залежностями: 
при передачі залежносте в конструктор, метод клас може робити будь що з цими залежностями.


4.В яких випадках краще використовувати ін’єкції залежностей за допомогою конструкторів, а в яких за допомогою сетерів?

Зазвичай в конструктори надаються обов’язкові залежності, без який клас не може функціонувати. Таким чином вже при створенні ми розуміємо, що клас повністю готовий до роботи, також можна ініціалізувати final поля. В сетери ін’єктуються опціональні залежності, без яких бін може існувати, або залежності, які можуть змінюватись в рантаймі.


5.В яких випадках краще використовувати біни типу singleton, а в яких prototype?

Singleton – кожного разу повертається один і той самий об'єкт, корисно для зберігання з'єднань, які є одними на додаток при відсутності стану. Prototype – окремий об'єкт для кожної ін'єкції, може бути використаний для зберігання стану сервісу чи певної логіки в окремому об'єкті.


6.Чи можливе у Spring Framework створення циркулярних залежностей при ін’єкціях?

З циклічною залежністю Spring не може вирішити, який із bean-компонентів повинен бути створений першим, оскільки вони залежать один від одного. У цих випадках виклик Spring викликає BeanCurrentlyInCreationException при завантаженні контексту.

7.Чи може бути в одному проекті кілька класів з реалізацією якогось інтерфейсу, якщо цей інтерфейс використовується для ін’єкції залежностей?

Так

8.Чи може бін мати кілька методів, помічених анотацією @Autowired?

Так, методів бін може мати скільки завгодно. Якщо метод позначений цією анотацією, то всі ці методи будуть виконані при створенні біна і аргументи в них передасть IOC контейнер.

9.Чи може бін мати кілька конструкторів, помічених анотацією @Autowired?

Ні, не може. Це неможливо, бо IOC контейнер використовує цю анотацію при створенні біна, відповідно він не вміє обирати який з двох конструкторів буде необхідний. 


**Лабораторна робота №3. Контрольні запитання.**

1.	Для чого потрібен шаблон проектування MVC.

MVC — архітектурний шаблон, який використовується під час проєктування та розробки програмного забезпечення.
Цей шаблон передбачає поділ системи на три взаємопов'язані частини: модель даних, вигляд (інтерфейс користувача) та модуль керування. Застосовується для відокремлення даних (моделі) від інтерфейсу користувача (вигляду) так, щоб зміни інтерфейсу користувача мінімально впливали на роботу з даними, а зміни в моделі даних могли здійснюватися без змін інтерфейсу користувача.

2.	В чому полягають переваги використання шаблону проектування Front Controller?

Серед переваг використання цього шаблону можна назвати простоту використання, оскільки точка входу централізована, усуває залежності між компонентами контролера та спрощує взаємодію. 

3.	Поясніть особливості використання шаблону проектування Front Controller при реалізації веб-застосунків та RESTful веб-сервісів.

4.	В чому полягають відмінності методів GET та POST протоколу HTTP?

Get запит не має тіла, є ідемпотентним, за специфікацією не змінює стан, може бути закешованим, на відміну від post методу

5.	Що таке сервлет?

Це Java клас, який вміє обробляти http запити. Саме бібліотека надає стандартизоване API для обробки запитів та генерації динамічного контенту на сервері.

6.	Якими способами передаються данні HTML-форм з клієнта на сервер? В чому полягають їх переваги і недоліки?

7.	Чи впливає регістр символів при передачі імен параметрів у HTTP-запитах до сервлетів?

Так

8.	Чи впливає регістр символів при передачі значень параметрів у HTTP-запитах до сервлетів?

Так

9.	В чому полягає різниця між статичними HTML-сторінками та шаблонами сторінок Thymeleaf та JSP?

Статичні сторінки не змінюють свого вмісту, якщо це необхідно зробити, потрібно відправляти нову згенеровану статичну сторінку. Динамічні сторінки змінюються в залежності від даних, що приходять з сервера.

10.	В чому полягає різниця між сервлетами та JSP-сторінками?

Перший це java клас контролер, а друге це динамічна сторінка. JSP легше читати, менше коду

11.	Як виглядає життєвий цикл JSP-сторінки? Що таке трансляція та компіляція JSP-сторінок?  

Трансляція – компіляція – завантаження в пам'ять – інсталяція – ініціалізація – обробка запитів (робота) – видалення.
Трансляція – це валідація коду сторінки та парсинг її у сервлет
Компіляція – тут компілюється вихідний код jsp класу  і створюється новий клас.

12.	Що таке скриптлети та директивні елементи?

Скриплети – елементи для написання java коду, а директивні елементи вказують веб контейнеру як необхідно транслювати сторінку в сервлет

13.	В чому полягає різниця між директивою <%@ include%> та елементом <jsp:include>?

Директива вставляє елемент на етапі трансляції, а активний елемент у процесі виконання

14.	Що таке JavaBean? Як їх використовувати у JSP-сторінках?

Це простий java клас, з методами getter/setter для кожного поля, пустий конструктор, та жодних інших методів. Використовується для передачі даних між сторінками.
<jsp:useBean id="myBean" class="mypackage.myBean"/>
<jsp:getProperty property="name" name="myBean"/>

15.	В чому полягають переваги/недоліки використання EL та JSTL у порівнянні з такими JSP-елементами як вирази (<%=…  %>) та скриптлети (<% … %>)?

EL сторений для легшого керування класами та об*эктами, а JSTL для написання xml скриптів замість java коду. Це полегшує читання, особливо для дизайнерів, що не знайомі з програмуванням чи java, але це вимагає від розробників знати додатковий синтаксис.

16.	В чому полягають переваги/недоліки використання JSTL-елемента <c:out> у порівнянні зі звичайними EL-виразом ${…} або JSP-елементом <%=… %>?

<c:out> елемент виводить інформацію на сторінку, його перевага в тому, що він не дасть виконатися потенційному скрипту, який міг прийти від користувача, а виведеться в тому ж вигляді

17.	Який час життя атрибутів на рівнях  requestScope, sessionScope, applicationScope? Чи можуть одночасно існувати атрибути з однаковими іменами на різних рівнях?

requestScope – атрибут зберігається лише в межах одного http запиту
sessionScope – зберігається в межах сесії користувача
applicationScope – зберігається в межах всього застосунку

18.	Що таке XSS-атака? Як зробити сайт стійким до таких атак?

Це така атака, коли в сайт, якому цілком можна довіряти, вставляється зловмисний скрипт, що має на меті взламати іншого користувача
Зазвичай, використовують додаткові бібліотеки для валідації та кодування даних, які не дозволять виконатися вставленому коду, це діє на javascript, html, css коди, також радять перевіряти url

19.	Поясніть різницю між термінами forward та redirect.

Перший передає запит на інший сервлет, ресурс або jsp. Redirect пересилає запит на інший додаток з клієнтської частини.


**Лабораторна робота №4. Контрольні запитання.**

1.	Поясніть різницю між вебзастосунками та RESTful вебсервісами.

Веб застосунок – це серверний застосунок, який обробляє запити через мережу інтернет.
RESTful вебсервіс – вебзастосунок, що працює через http протокол та слідує наступним правилам: має уніфікований інтерфейс, не зберігає стану, але може використати кеш, поділений на декілька слоїв.

2.	Які технології реалізації концепції сервісно-орієнтованої архітектури ви знаєте? Чим RESTful вебсервіси відрізняються від інших підходів?

Є RPC виклики через сторонній сервер, є gRPC напряму через http/2 але це не використовується при роботі з браузером. Також між сервісами може бути спілкування через повідомлення чи черги, як RabbitMQ чи kafka. Колись використовувався SOAP, та був витіснений RESTful сервісами, через більшу зручність. Також використовується WebSocket протокол, де потрібне постійне оновлення та обмін пакетами.
Restful більш уніфікований ніж різні типи rpc. В порівнянні з іншими підходами він є синхронним, інші цього не вимагають. 


3.	Поясніть особливості використання шаблону проектування Front Controller при реалізації вебзастосунків та RESTful вебсервісів.

Цей шаблон дає можливості задати всі основні процеси перед обробкою запиту, наприклад авторизація, у одному місці. Також при цьому всі http шляхи задані в одному місці, що є простішим для сприймання і ознайомлення, в інших мовах відомий як Router. Але цей шаблон дещо знижає гнучкість налаштування окремих шляхів.


4.	Для реалізації якої чи яких операцій CRUD (create, read, update, delete) можуть використовуватися такі методи протоколу HTTP як GET, POST, PUT, PATCH, DELETE? 

Create – post, іноді  put
Read – get, рідше post
Update – put, patch для часткового оновлення, іноді post
Delete – delete, іноді post


5.	Що означають поняття «безпечний» та «ідемпотентний» метод HTTP? Чи будь який безпечний метод є ідемпотентним? Чи будь який ідемпотентний метод є безпечним?

Безпечний метод не змінює стан серверу (читання). Ідемпотентний метод має той самий результат при багаторазовому використанні.
Чи будь який безпечний метод є ідемпотентним? – так
Чи будь який ідемпотентний метод є безпечним? - ні


6.	В чому полягає різниця між анотаціями @Controller та @RestController?

@Controller – позначає клас як Spring MVC контролер
@RestController – додає до @Controller анотацію @ResponseBody


7.	В чому полягає різниця між анотаціями @GetMapping та @RequestMapping?

@ GetMapping в собі ховає @RequestMapping(method = RequestMethod.GET)
@RequestMapping анотація для вказування spring ioc який метод оброблює певний запит

8.	В яких випадках можуть знадобитися анотації @RequestParam, @PathVariable, @RequestBody?

@PathVariable – some-path/{id}/other-path – змінна в шляху. Використовується коли є необхідний для запиту параметр, особливо в методах, що не мають тіла запиту. Наприклад ідентифікатор сутності, назва і тд.
@RequestParam – path?param=value – параметри в шляху. Використовується коли є необовязкові додаткові параметри, також ці параметри можуть використовуватися при різних шляхах. Наприклад параметр сортування, сторінки, кількість елементів, інше.
@RequestBody – тіло запиту. Використовується, коли необхідно передати сутність в запиті, оскільки передача в параметрах запиту для такої ситуації – погана практика. Оскільки це натякає на обовязковість параметрів.


9.	Для чого потрібен клас ResponseEntity?

Це generic клас-контейнер для керування відповіддю на http запит. Наприклад, налаштувати код відповіді.


10.	Як за допомогою анотації @Valid здійснювати валідацію даних, які надходять від клієнта?

У параметрах метода обробки запиту задається ця анотація перед об’єктом, що необхідно перевірити, в самому об’єкті визначаються анотації на поля, які потрібно валідувати, вони вказують, що саме необхідно перевірити. Наприклад 
@NotNull @Future
 private Date date;


**Лабораторна робота №5. Контрольні запитання.**


1.	Поясніть різницю між JDBC та JdbcTemplate.

Використовуючи JdbcTemplate, ви використовуєте доступ нижчого рівня, з більшою гнучкістю, але, можливо, і більш шаблонним кодом.
Spring JdbcTemplate легше використовувати з екзотичними схемами баз даних і фокусом на процедурах, що зберігаються. Використовуючи JPA, необхідно переконатися, що схема бази даних правильно зіставляється з моделлю предметної області.
Обидві технології потребують розробників, які знають реляційні бази даних, SQL та транзакції. Однак з JPA ви отримуєте більше прихованої складності.
JPA легко підключається до рівнів кешування даних, оскільки об'єктно-орієнтований фокус спрощує ідентифікацію, оновлення та анулювання запису в кеші.
Ще один аспект, який слід враховувати, полягає в тому, що хоча з JPA ви отримуєте модель предметної області для своєї схеми бази даних, вам часто потрібно використовувати додаткові класи DTO. Використовуючи JdbcTemplate, ви можете працювати безпосередньо з класами DTO.

2.	Які переваги надає PreparedStatement у порівнянні зі звичайним Statement?

Statement використовуватиметься для виконання статичних інструкцій SQL і не може приймати вхідні параметри. PreparedStatement використовуватиметься для багаторазового динамічного виконання операторів SQL. Він прийматиме вхідні параметри

3.	Поясніть різницю між методами execute(), executeQuery() та executeUpdate() класу PreparedStatement.

executeUpdate(): виконує такі SQL-команди, як INSERT, UPDATE, DELETE, CREATE та повертає кількість змінених рядків

executeQuery(): виконує команду SELECT, яка повертає дані у вигляді ResultSet

execute(): виконує будь-яку SQL-команду

4.	Чим відрізняються інтерфейси RowMapper<T> та ResultSetExtractor<T>?

RowMapper
Це функціональний інтерфейс, тому його можна використовувати як ціль призначення для лямбда-виразу або посилання на метод. Інтерфейс відображення рядків використовується для отримання записів з будь-якої бази даних за допомогою методу query() класу JdbcTemplate шляхом передачі екземпляра відображувача рядків.
Його об’єкти, як правило, не мають стану, тому їх можна повторно використовувати; вони є ідеальним вибором для реалізації логіки відображення рядків в одному місці.
Зазвичай це простіший вибір для обробки ResultSet.
ResultSetExtractor
Це функціональний інтерфейс, тому його можна використовувати як ціль призначення для лямбда-виразу або посилання на метод. ResultSetExtractor використовується для отримання записів із бази даних за допомогою методу query() класу JdbcTemplate, де нам потрібно передати екземпляр ResultSetExtractor.
Його об’єкт, як правило, не має стану, і тому його можна повторно використовувати, доки він не отримує доступу до ресурсів із збереженням стану або не зберігає отриманий стан у межах об’єкта.
Реалізації інтерфейсу Result Extractor виконують роботу з вилучення результатів із ResultSet.

5.	Для чого потрібен інтерфейс KeyHolder?

Інтерфейс KeyHolder призначений для отримання ключів, які зазвичай використовуються для автоматично згенерованих ключів, які потенційно повертаються операторами вставки JDBC. Реалізації цього інтерфейсу можуть містити будь-яку кількість ключів. У загальному випадку ключі повертаються як список, що містить одну карту для кожного рядка ключів.

6.	Як працює декларативне керування транзакціями у Spring Framework?

Декларативне управління транзакціями означає відділення управління транзакціями від бізнес-логіки. Розробник використовує лише анотації у конфігурації на основі XML для керування транзакціями.
Декларативне управління полягає в перехопленні до і після методу, потім створенні або додаванні транзакції до запуску цільового методу та відправці або відкаті транзакції відповідно до виконання після виконання цільового методу. Найбільшою перевагою декларативних транзакцій є те, що не потрібно керувати за допомогою програмування, тому немає необхідності додавати код управління транзакціями в код бізнес-логіки. Потрібно тільки зробити відповідні оголошення правил транзакцій у конфігураційному файлі (або через @Transactional Анотація), можна застосувати правила транзакцій до бізнес-логіки.

7.	Що таке «transaction propagation»? Як обрати потрібний механізм поширення транзакцій? Який механізм поширення транзакцій використовується за замовченням? 

Розповсюдження транзакції вказує, чи буде будь-який компонент або служба брати участь у транзакції чи ні, і як він поводитиметься, якщо викликаючий компонент/служба вже має або не має створеної транзакції.
Spring дозволяє контролювати поведінку логічних і фізичних транзакцій за допомогою механізмів поширення транзакцій. Є сім типів механізмів розповсюдження транзакцій, за допомогою яких ви можете налаштувати у програмі Spring.

Propagation.REQUIRED є налаштуванням за замовчуванням для анотації @Transactional.

Propagation.REQUIRES_NEW вказує контейнеру Spring завжди створювати нову фізичну транзакцію. Такі транзакції також можуть оголошувати власні налаштування тайм-ауту, тільки для читання та рівня ізоляції і не успадковувати характеристики зовнішньої фізичної транзакції.

NESTED діє як REQUIRED, тільки використовує точки збереження між вкладеними дзвінками. Інакше кажучи, внутрішні логічні транзакції можуть виконувати відкат незалежно від зовнішніх логічних транзакцій.

Propagation.MANDATORY вимагає існуючої фізичної транзакції.

Вказує Propagation.NEVER, що не повинно бути ніякої фізичної транзакції. Якщо буде знайдено фізичну транзакцію, це NEVER викликає виняток.

Propagation.NOT_SUPPORTED вказує, що якщо фізична транзакція існує, то вона буде призупинена перед продовженням. Ця фізична транзакція буде автоматично відновлена наприкінці. Після відновлення цієї транзакції її можна відкотити (у разі збою) або зафіксувати.

Propagation.SUPPORTS зазначає, що якщо фізична транзакція існує, то вона виконуватиме розмежований метод як логічну транзакцію в контексті цієї фізичної транзакції. В іншому випадку він виконає цей метод поза фізичною транзакцією.


**Лабораторна робота №6. Контрольні запитання.**

1.	Що таке ORM?

ORM — технологія програмування, яка зв'язує бази даних з концепціями об'єктно-орієнтованих мов програмування, створюючи «віртуальну об'єктну базу даних».
Іншими словами, ви можете розглядати ORM як рівень, який з’єднує об’єктно-орієнтоване програмування (ООП) з реляційними базами даних.

2.	В чому полягає різниця між JPA та Hibernate?

JPA — це специфікація Java, а не реалізація. Hibernate є реалізацією JPA і використовує загальні стандарти Java Persistence API. Це стандартний API, який дозволяє розробникам плавно виконувати операції з базою даних. Він використовується для зіставлення типів даних Java із таблицями бази даних і типами даних SQL.

3.	Поясніть призначення кожного методу з інтерфейсу CrudRepository.

count() Повертає кількість доступних об’єктів.
delete(T entity) Видаляє вказану сутність.
deleteAll() Видаляє всі сутності, якими керує репозиторій.
deleteAll(Iterable<? extends ID> entities) Видаляє вказані сутності.
deleteAllById(Iterable<? extends ID> ids) Видаляє всі екземпляри типу T із заданими ідентифікаторами.
deleteById(ID id) Видаляє сутність із заданим ідентифікатором.
existsById(ID id) Повертає, чи існує об’єкт із заданим ідентифікатором.
findAll() Повертає всі екземпляри типу.
findAllById(Iterable<ID> ids) Повертає всі екземпляри типу T із заданими ідентифікаторами.
findById(ID id) Отримує сутність за її ідентифікатором.
save(S entity) Зберігає вказану сутність.
saveAll(Iterable <S> entities) Зберігає всі задані сутності.


4.	Яким вимогам має відповідати @Entity-клас?

Клас @Entity повинен відповідати цим вимогам.

•	Клас повинен бути анотований анотацією javax.persistence.Entity.

•	Клас повинен мати відкритий або захищений конструктор без аргументів. Клас може мати інші конструктори.

•	Класи не можна оголошувати final. Жодні методи чи постійні змінні екземпляра не повинні оголошуватися final.

•	Якщо примірник сутності передається за значенням як відокремлений об’єкт, наприклад через віддалений бізнес-інтерфейс сеансового компонента, клас повинен реалізувати інтерфейс Serializable.

•	Сутності можуть розширювати як класи сутностей, так і класи, що не є сутностями, а класи, що не є сутностями, можуть розширювати класи сутностей.

•	Постійні змінні екземпляра мають бути оголошені приватними, захищеними або приватними для пакетів, і до них можна отримати прямий доступ лише за допомогою методів класу сутності. Клієнти повинні отримати доступ до стану сутності за допомогою засобів доступу або бізнес-методів.


5.	Які є типи відношень між сутностями у JPA?

Відносини сутностей JPA

•	ManyToOne entity relation.

Відношення « ManyToOne » між сутностями: де сутність пов’язана одна з одною (стовпцем або групою стовпців), яка містить унікальні значенняЦі зв'язки застосовуються до реляційних баз даних із використанням зовнішнього ключа між таблицями.

•	OneToMany entity relation.

У цьому відношенні багато дітей в іншій сутності пов’язані з кожним рядком сутності. Важливо те, що декілька батьків не можуть записувати записи дітей. Кожен рядок у таблиці A пов’язаний з 0,1 або більше рядками таблиці B у зв’язку «один до багатьох» між таблицями A та B.

•	OneToOne entity relation.

Один елемент може належати лише одному іншому елементу у відношенні сутності «один до одного». Це означає, що кожен рядок сутності стосується лише одного рядка іншої сутності.

•	ManyToMany entity relation

Відношення «багато-до-багатьох» — це один або кілька рядків однієї сутності, які з’єднані з кількома рядками в іншій сутності.


6.	Для чого потрібні DTO? Чому замість них не завжди можна використовувати @Entity об’єкти?

Об’єкт передачі даних (DTO) — це об’єкт, який переносить дані між процесами. Ви можете використовувати цю техніку, щоб полегшити зв’язок між двома системами (наприклад, API і вашим сервером) без потенційного розкриття конфіденційної інформації.
Різниця між DTO та Entity:
Entity — це клас, зіставлений із таблицею. Dto — це клас, який здебільшого відображається на шарі «view». Те, що потрібно зберегти, це Entity, а те, що потрібно «показати» на веб-сторінці, — це DTO.

