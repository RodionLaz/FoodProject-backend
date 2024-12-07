package com.example.foodP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodPApplication {



	/*
1. תיקיות וקטגוריות עיקריות:
- config: הגדרות קונפיגורציה כלליות עבור המערכת (כמו הגדרות Security, CORS, Swagger).
- controller: מנהלי בקרות (Controllers) לממשקי ה-API, המפנים את הבקשות לפונקציות המתאימות ב-service.
- exception: טיפול בשגיאות (Custom exception handling) ובקרת שגיאות דרך Spring AOP.
- model: מבני נתונים (Entities), מודלים למידע (כמו User, Restaurant, MenuItem, Order).
- repository: גישה למסד הנתונים (Database) בעזרת Spring Data JPA.
- service: לוגיקת אפליקציה (העסקית) שפועלת בין ה-controller וה-repository.
- util: פונקציות עזר (Helper classes), אולי גם עם קוד שקשור לאבטחה, אוטומציות או טפסים.
- security: (אם לא הכנסתי אותו תחת config) הגדרות אבטחה - כמו קונפיגורציה של Spring Security עם JWT, OAuth2 או כל שיטה אחרת לאימות והזדהות.

2. קבצים שיכולים להיות שימושיים:
1. Controller:
   - UserController – טופס הרשמה/כניסה, ניהול פרופיל משתמש.
   - RestaurantController – ניהול פרופיל מסעדה, הוספה/עריכה/מחיקה של מנות.
   - OrderController – הגשה, ביצוע וניהול הזמנות.
   - PaymentController – אינטגרציה עם ספקי תשלום כמו Stripe או PayPal.
   - MenuController – ניהול התפריטים והמנות.
   - AdminController – למנהלי מערכת: ניהול משתמשים, מסעדות ודוחות.
   
2. Service:
   - UserService – לוגיקה לניהול משתמשים (הזדהות, ניהול פרופיל).
   - RestaurantService – לוגיקה לניהול מסעדות ותפריטים.
   - OrderService – לוגיקה לניהול הזמנות ותשלומים.
   - PaymentService – אינטגרציה עם ספקי תשלום.
   - AdminService – לוגיקה לניהול מערכת ומעקב אחר פעילות.
   - NotificationService – אם יש לך צורך לשלוח הודעות Push או התראות בזמן אמת.
   
3. Model (Entities):
   - User – משתמש במערכת (כולל פרטי פרופיל).
   - Restaurant – מסעדה, כולל פרטים כמו שעות עבודה, מיקום, תמונות, ועוד.
   - MenuItem – פרטי המנות (שם, תיאור, מחיר, תמונה).
   - Order – פרטי הזמנה (הזמנות, סטטוס, פרטי תשלום).
   - Payment – נתוני תשלום (סכום, תאריך, סטטוס).
   - Promotion – קופונים או מבצעים שמנוהלים במערכת.
   - Role – הרשאות וגישה למשתמשים (למשל, Admin, RestaurantOwner, Customer).

4. Repository:
   - UserRepository – גישה לנתוני משתמשים במסד נתונים.
   - RestaurantRepository – גישה למידע על מסעדות.
   - OrderRepository – גישה להזמנות.
   - MenuItemRepository – גישה למנות בתפריטים.
   - PaymentRepository – גישה לפרטי תשלומים.
   - RoleRepository – גישה לתפקידים והרשאות.

5. Security:
   - JwtAuthenticationEntryPoint – טיפול בשגיאות עבור JWT.
   - JwtTokenProvider – יצירת תוקף (token) ואימות JWT.
   - JwtAuthenticationFilter – מסנן JWT לאימות כל בקשה.
   - CustomUserDetailsService – אם יש צורך להתאים את ההזדהות למודל מותאם אישית.

6. Exception:
   - CustomException – אם יש צורך לטפל בשגיאות מותאמות אישית כמו שגיאות לא בהצלחה במהלך ההזמנה.
   - GlobalExceptionHandler – טיפול בשגיאות גלובלי בעזרת @ControllerAdvice.

7. Other (optional):
   - SwaggerConfig – הגדרת Swagger/OpenAPI עבור תיעוד ה-API.
   - DatabaseMigration – הגדרת כלי כמו Flyway או Liquibase למיגרציה של מסד הנתונים.
   - CacheConfig – אם אתה רוצה להשתמש ב-caching (Redis או אחרים).
   - AsyncConfig – אם המערכת תומכת בתהליכים אסינכרוניים (כמו שליחת מיילים או התראות בזמן אמת).
*/

	public static void main(String[] args) {
		SpringApplication.run(FoodPApplication.class, args);

	}


}

