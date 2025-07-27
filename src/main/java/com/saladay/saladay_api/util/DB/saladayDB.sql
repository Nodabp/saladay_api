use saladayDB;

INSERT INTO menu (name, description, price, is_active, category_id) VALUES
                                                                        ('불고기 샐러드', '직화 불고기 + 야채 + 현미', 7500, 1, 1),
                                                                        ('치킨 시저 샐러드', '닭가슴살 + 시저드레싱', 6800, 1, 1),
                                                                        ('연어 샐러드', '훈제 연어 + 믹스 채소', 8200, 1, 1),
                                                                        ('병아리콩 샐러드', '채식용 + 단백질 보강', 6500, 1, 1),
                                                                        ('베이컨 에그 샐러드', '베이컨, 반숙란, 로메인', 7000, 1, 1);

INSERT INTO menu (name, description, price, is_active, category_id) VALUES
                                                                        ('통감자 구이', '에어프라이드 방식', 2000, 1, 2),
                                                                        ('미니 단호박구이', '간단한 당류 보충', 2300, 1, 2),
                                                                        ('곡물빵 (1개)', '고소한 식감', 1800, 1, 2);

INSERT INTO menu (name, description, price, is_active, category_id) VALUES
                                                                        ('콜라', '탄산음료', 1500, 1, 3),
                                                                        ('사이다', '탄산음료', 1500, 1, 3),
                                                                        ('아이스티 (복숭아)', '달콤한 음료', 1700, 1, 3),
                                                                        ('생수', '무가당', 1000, 1, 3);

INSERT INTO menu (name, description, price, is_active, category_id) VALUES
    ('병맥주 (330ml)', '성인 인증 필요', 3800, 1, 4);

INSERT INTO menu_option (menu_id, name, extra_price, type, is_default, is_required, display_order)
VALUES
    (2, '치즈 추가', 500, 'TOPPING', false, false, 1),
    (2, '아보카도 추가', 1000, 'TOPPING', false, false, 2),
    (3, '밥 추가', 1000, 'EXTRA', false, true, 1);

INSERT INTO discount (type, value, start_time, end_time, is_active, target_type, target_category_id, description, stackable, priority)
VALUES
    ('PERCENT', 10, '2025-07-01 00:00:00', '2025-07-31 23:59:59', true, 'CATEGORY', 1, '여름 샐러드 할인', false, 1);

INSERT INTO users (phone_number, name, gender, birth, create_at, updated_at)
VALUES
    ('01012345678', '김샐러', 'F', '1993-06-15', NOW(), NOW());

ALTER TABLE menu ADD COLUMN image_url VARCHAR(255);
ALTER TABLE menu_option ADD COLUMN image_url VARCHAR(255);
