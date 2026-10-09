
INSERT INTO category (category_id, category_name, is_active, parent_id) VALUES
(1, 'Chăm sóc sức khỏe', TRUE, NULL),
(2, 'Làm đẹp & Spa', TRUE, NULL),
(3, 'Thể thao & Fitness', TRUE, NULL),
(4, 'Massage trị liệu', TRUE, 1),
(5, 'Chăm sóc da mặt', TRUE, 2),
(6, 'Tóc & Nail', TRUE, 2),
(7, 'Yoga & Pilates', TRUE, 3),
(8, 'Gym & Personal Trainer', TRUE, 3),
(9, 'Massage bấm huyệt', TRUE, 4),
(10, 'Trị mụn chuyên sâu', TRUE, 5),
(11, 'Cắt tóc nam nữ', TRUE, 6),
(12, 'Làm móng nghệ thuật', TRUE, 6),
(13, 'Chăm sóc toàn thân', TRUE, 2),
(14, 'Vật lý trị liệu', TRUE, 1),
(15, 'Trang điểm dự tiệc', TRUE, 2);


INSERT INTO merchant (merchant_id, email, display_name, registered_at, verification_status) VALUES
(101, 'contact@spamaianh.vn', 'Spa Mai Anh', '2026-01-10 08:30:00', 'VERIFIED'),
(102, 'info@hairsalonpro.com', 'Hair Salon Pro', '2026-01-12 09:15:00', 'VERIFIED'),
(103, 'support@fitnesshub.vn', 'Fitness Hub Center', '2026-01-15 10:00:00', 'VERIFIED'),
(104, 'massage.trilieu@gmail.com', 'An Binh Therapy', '2026-01-20 14:20:00', 'VERIFIED'),
(105, 'beauty.queen@gmail.com', 'Queen Beauty Bar', '2026-02-01 11:00:00', 'VERIFIED'),
(106, 'yoga.life@gmail.com', 'Zen Yoga Studio', '2026-02-05 16:45:00', 'VERIFIED'),
(107, 'nail.art.house@gmail.com', 'Nail Art Studio', '2026-02-10 08:00:00', 'VERIFIED'),
(108, 'nam.barber@gmail.com', 'Nam Barber Shop', '2026-02-12 13:30:00', 'VERIFIED'),
(109, 'duoclieu.viet@gmail.com', 'Đông Y Duoc Lieu', '2026-02-18 15:10:00', 'VERIFIED'),
(110, 'pvt.fit@gmail.com', 'PT Fitness Private', '2026-02-22 09:00:00', 'VERIFIED'),
(111, 'skincare.lab@gmail.com', 'SkinCare Lab Center', '2026-03-01 10:30:00', 'PENDING'),
(112, 'spa.mattroi@gmail.com', 'Sun Spa & Clinic', '2026-03-05 11:20:00', 'PENDING'),
(113, 'hairstudio.lan@gmail.com', 'Lan Hair Design', '2026-03-10 14:00:00', 'REJECTED'),
(114, 'massage.saigon@gmail.com', 'Saigon Massage', '2026-03-12 16:00:00', 'VERIFIED'),
(115, 'makeupsara@gmail.com', 'Sara Makeup Corner', '2026-03-15 17:30:00', 'VERIFIED');


INSERT INTO individual_merchant (merchant_id, first_name, last_name, national_id, date_of_birth) VALUES
(101, 'Anh', 'Mai', '079095000101', '1992-05-15'),
(102, 'Bình', 'Lê', '079095000102', '1988-10-20'),
(104, 'An', 'Trần', '079095000104', '1990-03-12'),
(105, 'Chi', 'Nguyễn', '079095000105', '1995-07-25'),
(106, 'Dương', 'Phạm', '079095000106', '1985-12-01'),
(107, 'Giang', 'Đỗ', '079095000107', '1994-09-18'),
(108, 'Nam', 'Vũ', '079095000108', '1991-04-30'),
(109, 'Hùng', 'Hoàng', '079095000109', '1982-11-11'),
(110, 'Tuấn', 'Ngô', '079095000110', '1996-01-08'),
(111, 'Linh', 'Bùi', '079095000111', '1993-06-22'),
(112, 'Sơn', 'Đặng', '079095000112', '1989-08-14'),
(113, 'Lan', 'Trịnh', '079095000113', '1997-02-28'),
(114, 'Phúc', 'Lương', '079095000114', '1986-05-04'),
(115, 'Hương', 'Phan', '079095000115', '1998-10-10'),
(103, 'Quang', 'Đinh', '079095000103', '1987-03-25');


INSERT INTO business_merchant (merchant_id, legal_name, business_registration_no, tax_number, contact_person) VALUES
(101, 'Công ty TNHH Mai Anh Beauty', '0312345601', '0312345601-001', 'Mai Thị Anh'),
(102, 'Công ty Cổ phần Hair Pro', '0312345602', '0312345602-001', 'Lê Văn Bình'),
(103, 'Công ty TNHH Fitness Hub', '0312345603', '0312345603-001', 'Đinh Văn Quang'),
(104, 'Công ty TNHH Massage An Bình', '0312345604', '0312345604-001', 'Trần Văn An'),
(105, 'Công ty TNHH Queen Beauty', '0312345605', '0312345605-001', 'Nguyễn Kim Chi'),
(106, 'Hộ kinh doanh Zen Yoga Studio', '0312345606', '0312345606-001', 'Phạm Ánh Dương'),
(107, 'Hộ kinh doanh Nail Art House', '0312345607', '0312345607-001', 'Đỗ Hương Giang'),
(108, 'Hộ kinh doanh Nam Barber', '0312345608', '0312345608-001', 'Vũ Văn Nam'),
(109, 'Công ty TNHH Dược Liệu Việt', '0312345609', '0312345609-001', 'Hoàng Quốc Hùng'),
(110, 'Công ty TNHH PT Fitness', '0312345610', '0312345610-001', 'Ngô Anh Tuấn'),
(111, 'Công ty TNHH SkinCare Lab', '0312345611', '0312345611-001', 'Bùi Thùy Linh'),
(112, 'Công ty TNHH Sun Spa', '0312345612', '0312345612-001', 'Đặng Thái Sơn'),
(113, 'Hộ kinh doanh Lan Hair', '0312345613', '0312345613-001', 'Trịnh Thị Lan'),
(114, 'Công ty TNHH Saigon Massage', '0312345614', '0312345614-001', 'Lương Hồng Phúc'),
(115, 'Hộ kinh doanh Sara Makeup', '0312345615', '0312345615-001', 'Phan Thu Hương');


INSERT INTO service (service_id, merchant_id, category_id, title, description, list_price, duration, is_offered) VALUES
(201, 101, 5, 'Chăm sóc da mặt chuyên sâu', 'Liệu trình làm sạch và cấp ẩm da 60 phút', 350000.00, 60, TRUE),
(202, 101, 10, 'Trị mụn chuẩn y khoa', 'Kháng viêm và lấy nhân mụn an toàn', 450000.00, 75, TRUE),
(203, 102, 11, 'Cắt tóc nam nữ tạo kiểu', 'Bao gồm gội đầu và sấy tạo kiểu', 150000.00, 45, TRUE),
(204, 102, 6, 'Uốn tóc Hàn Quốc', 'Uốn phồng tự nhiên giữ nếp lâu', 600000.00, 120, TRUE),
(205, 103, 8, 'Gói tập Gym 1 tháng', 'Tự do tập luyện không giới hạn khung giờ', 500000.00, 60, TRUE),
(206, 104, 4, 'Massage cổ vai gáy', 'Giải tỏa căng thẳng nhức mỏi', 250000.00, 45, TRUE),
(207, 104, 9, 'Massage bấm huyệt toàn thân', 'Thông kinh lạc và phục hồi sức khỏe', 400000.00, 90, TRUE),
(208, 105, 13, 'Tẩy tế bào chết toàn thân', 'Giúp da sáng mịn rạng rỡ', 300000.00, 50, TRUE),
(209, 106, 7, 'Thẻ tập Yoga 10 buổi', 'Tập luyện với giáo viên giàu kinh nghiệm', 800000.00, 60, TRUE),
(210, 107, 12, 'Sơn Gel và vẽ móng nghệ thuật', 'Làm móng đẹp theo yêu cầu', 200000.00, 60, TRUE),
(211, 108, 11, 'Cắt tóc Nam Barber Classic', 'Cắt và tạo kiểu chuẩn đấng mày râu', 100000.00, 30, TRUE),
(212, 109, 14, 'Vật lý trị liệu cột sống', 'Giảm đau thắt lưng hiệu quả', 500000.00, 60, TRUE),
(213, 110, 8, 'Kèm PT 1-1 giảm cân', 'Lên lộ trình tập luyện và dinh dưỡng', 350000.00, 60, TRUE),
(214, 114, 4, 'Massage đá nóng', 'Thư giãn cơ bắp bằng đá nóng Himalaya', 450000.00, 75, TRUE),
(215, 115, 15, 'Trang điểm tiệc cưới / sự kiện', 'Makeup phong cách trong trẻo tự nhiên', 500000.00, 60, TRUE);


INSERT INTO customer (customer_id, email, password_hash, first_name, last_name, registered_at) VALUES
(301, 'nhat.nguyen@gmail.com', 'hash_pass_301', 'Nhật', 'Nguyễn', '2026-03-01 08:00:00'),
(302, 'minh.tran@gmail.com', 'hash_pass_302', 'Minh', 'Trần', '2026-03-02 09:30:00'),
(303, 'hoa.le@gmail.com', 'hash_pass_303', 'Hoa', 'Lê', '2026-03-03 10:15:00'),
(304, 'tuan.pham@gmail.com', 'hash_pass_304', 'Tuấn', 'Phạm', '2026-03-04 11:45:00'),
(305, 'huong.vo@gmail.com', 'hash_pass_305', 'Hương', 'Võ', '2026-03-05 13:20:00'),
(306, 'duc.hoang@gmail.com', 'hash_pass_306', 'Đức', 'Hoàng', '2026-03-06 14:00:00'),
(307, 'trinh.dang@gmail.com', 'hash_pass_307', 'Trinh', 'Đặng', '2026-03-07 15:30:00'),
(308, 'khanh.vu@gmail.com', 'hash_pass_308', 'Khánh', 'Vũ', '2026-03-08 16:10:00'),
(309, 'linh.do@gmail.com', 'hash_pass_309', 'Linh', 'Đỗ', '2026-03-09 17:05:00'),
(310, 'hai.bui@gmail.com', 'hash_pass_310', 'Hải', 'Bùi', '2026-03-10 18:25:00'),
(311, 'lan.nguyen@gmail.com', 'hash_pass_311', 'Lan', 'Nguyễn', '2026-03-11 19:00:00'),
(312, 'thinh.phan@gmail.com', 'hash_pass_312', 'Thịnh', 'Phan', '2026-03-12 20:15:00'),
(313, 'vy.truong@gmail.com', 'hash_pass_313', 'Vy', 'Trương', '2026-03-13 21:00:00'),
(314, 'duy.dinh@gmail.com', 'hash_pass_314', 'Duy', 'Đinh', '2026-03-14 22:10:00'),
(315, 'mai.ly@gmail.com', 'hash_pass_315', 'Mai', 'Lý', '2026-03-15 23:00:00');


INSERT INTO payment (payment_id, amount_paid, payment_method, payment_status, platform_commission, payment_date) VALUES
(401, 350000.00, 'MOMO', 'COMPLETED', 35000.00, '2026-09-01 10:00:00'),
(402, 150000.00, 'BANK_TRANSFER', 'COMPLETED', 15000.00, '2026-09-02 11:30:00'),
(403, 500000.00, 'CASH', 'COMPLETED', 50000.00, '2026-09-03 14:00:00'),
(404, 250000.00, 'VNPay', 'COMPLETED', 25000.00, '2026-09-05 09:15:00'),
(405, 800000.00, 'MOMO', 'COMPLETED', 80000.00, '2026-09-08 16:20:00'),
(406, 200000.00, 'CREDIT_CARD', 'COMPLETED', 20000.00, '2026-09-10 13:45:00'),
(407, 100000.00, 'CASH', 'COMPLETED', 10000.00, '2026-09-12 10:30:00'),
(408, 450000.00, 'VNPay', 'COMPLETED', 45000.00, '2026-09-15 15:00:00'),
(409, 350000.00, 'BANK_TRANSFER', 'COMPLETED', 35000.00, '2026-09-18 17:10:00'),
(410, 500000.00, 'MOMO', 'COMPLETED', 50000.00, '2026-09-20 18:30:00'),
(411, 450000.00, 'VNPay', 'PENDING', 0.00, '2026-10-01 08:00:00'),
(412, 600000.00, 'BANK_TRANSFER', 'FAILED', 0.00, '2026-10-02 09:00:00'),
(413, 300000.00, 'MOMO', 'REFUNDED', 0.00, '2026-10-03 10:00:00'),
(414, 400000.00, 'CASH', 'COMPLETED', 40000.00, '2026-10-05 14:30:00'),
(415, 500000.00, 'VNPay', 'COMPLETED', 50000.00, '2026-10-07 16:00:00');


INSERT INTO booking (booking_id, customer_id, service_id, payment_id, booking_datetime, status, charged_price, customer_note) VALUES
(501, 301, 201, 401, '2026-09-01 10:00:00', 'COMPLETED', 350000.00, 'Làm nhẹ nhàng giúp em'),
(502, 302, 203, 402, '2026-09-02 11:30:00', 'COMPLETED', 150000.00, 'Cắt kiểu Layer'),
(503, 303, 205, 403, '2026-09-03 14:00:00', 'COMPLETED', 500000.00, 'Tư vấn tập cardio'),
(504, 304, 206, 404, '2026-09-05 09:15:00', 'COMPLETED', 250000.00, 'Đau vai gáy nặng'),
(505, 305, 209, 405, '2026-09-08 16:20:00', 'COMPLETED', 800000.00, 'Lớp Yoga cơ bản'),
(506, 306, 210, 406, '2026-09-10 13:45:00', 'COMPLETED', 200000.00, 'Mẫu móng Pastel'),
(507, 307, 211, 407, '2026-09-12 10:30:00', 'COMPLETED', 100000.00, 'Cắt ngắn gọn'),
(508, 308, 202, 408, '2026-09-15 15:00:00', 'COMPLETED', 450000.00, 'Da nhạy cảm'),
(509, 309, 213, 409, '2026-09-18 17:10:00', 'COMPLETED', 350000.00, 'Tập cơ bụng'),
(510, 310, 215, 410, '2026-09-20 18:30:00', 'COMPLETED', 500000.00, 'Makeup tone cam'),
(511, 311, 202, 411, '2026-10-01 08:00:00', 'REQUESTED', 450000.00, 'Đến đúng giờ'),
(512, 312, 204, 412, '2026-10-02 09:00:00', 'CANCELLED', 600000.00, 'Bận việc đột xuất'),
(513, 313, 208, 413, '2026-10-03 10:00:00', 'CANCELLED', 300000.00, 'Đổi lịch sang tuần'),
(514, 314, 207, 414, '2026-10-05 14:30:00', 'CONFIRMED', 400000.00, 'Chuẩn bị phòng yên tĩnh'),
(515, 315, 212, 415, '2026-10-07 16:00:00', 'CONFIRMED', 500000.00, 'Bác sĩ tư vấn trước');


INSERT INTO review (review_id, booking_id, score, comment, created_at) VALUES
(601, 501, 5, 'Dịch vụ rất tốt, nhân viên thân thiện!', '2026-09-01 12:00:00'),
(602, 502, 4, 'Cắt tóc đẹp, vừa ý mình.', '2026-09-02 13:00:00'),
(603, 503, 5, 'Phòng tập sạch sẽ, máy móc hiện đại.', '2026-09-03 16:00:00'),
(604, 504, 5, 'Massage xong hết hẳn đau mỏi.', '2026-09-05 11:00:00'),
(605, 505, 4, 'Giáo viên hướng dẫn nhiệt tình.', '2026-09-08 18:00:00'),
(606, 506, 5, 'Sơn móng giữ được rất lâu!', '2026-09-10 15:30:00'),
(607, 507, 4, 'Barber vui tính, cắt nhanh gọn.', '2026-09-12 11:30:00'),
(608, 508, 5, 'Trị mụn êm, không bị sưng đỏ.', '2026-09-15 17:00:00'),
(609, 509, 5, 'PT hướng dẫn cực kỳ chu đáo.', '2026-09-18 19:00:00'),
(610, 510, 5, 'Makeup tươi tắn, ai cũng khen.', '2026-09-20 20:00:00'),
(611, 511, 4, 'Trải nghiệm khá ổn định.', '2026-10-01 12:00:00'),
(612, 512, 3, 'Hủy đơn nhanh chóng, hỗ trợ tốt.', '2026-10-02 11:00:00'),
(613, 513, 4, 'Phục vụ chu đáo, sẽ quay lại!', '2026-10-03 13:00:00'),
(614, 514, 4, 'Dịch vụ đặt trước rất ok.', '2026-10-05 16:00:00'),
(615, 515, 5, 'Rất hài lòng với liệu trình.', '2026-10-07 18:00:00');


INSERT INTO customer_saved_service (customer_id, service_id, saved_at) VALUES
(301, 201, '2026-03-02 08:00:00'),
(301, 202, '2026-03-02 08:30:00'),
(302, 203, '2026-03-03 09:00:00'),
(302, 204, '2026-03-03 09:15:00'),
(303, 205, '2026-03-04 10:00:00'),
(304, 206, '2026-03-05 11:00:00'),
(304, 207, '2026-03-05 11:30:00'),
(305, 209, '2026-03-06 14:00:00'),
(306, 210, '2026-03-07 15:00:00'),
(307, 211, '2026-03-08 16:00:00'),
(308, 202, '2026-03-09 17:00:00'),
(309, 213, '2026-03-10 18:00:00'),
(310, 215, '2026-03-11 19:00:00'),
(311, 201, '2026-03-12 20:00:00'),
(312, 214, '2026-03-13 21:00:00');