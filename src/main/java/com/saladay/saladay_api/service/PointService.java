package com.saladay.saladay_api.service;

import com.saladay.saladay_api.domain.enums.PointSource;
import com.saladay.saladay_api.domain.enums.PointType;
import com.saladay.saladay_api.domain.point.Point;
import com.saladay.saladay_api.domain.users.Users;
import com.saladay.saladay_api.dto.pointDTO.PointRequestDTO;
import com.saladay.saladay_api.dto.pointDTO.PointResponseDTO;
import com.saladay.saladay_api.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class PointService {

    private final UserService userService;
    private final PointRepository pointRepository;

    public PointResponseDTO process(PointRequestDTO request) {

        // 1. 사용자 식별
        Long userId = (request.getUserId() != null)
                ? request.getUserId()
                : userService.findByPhoneNumber(request.getPhoneNumber()).getId();

        // 2. 금액 부호 처리
        int convertedAmount = (request.getType() == PointType.USING)
                ? -Math.abs(request.getPointAmount())
                : Math.abs(request.getPointAmount());

        // 3. Point 생성
        Point point = Point.builder()
                .users(Users.builder().id(userId).build())
                .pointAmount(convertedAmount)
                .type(request.getType())
                .source(request.getSource())
                .description(request.getDescription())
                .expiredAt(request.getExpiredAt())
                .relatedOrderId(request.getRelatedOrderId())
                .build();

        pointRepository.save(point);

        // 4. 응답 DTO 반환
        return PointResponseDTO.from(point);
    }
    public List<PointResponseDTO> getPoints(Long userId, String phoneNumber) {
        Long resolvedUserId = (userId != null)
                ? userId
                : userService.findByPhoneNumber(phoneNumber).getId();

        List<Point> points = pointRepository.findByUsersIdOrderByCreatedAtDesc(resolvedUserId);
        return points.stream()
                .map(PointResponseDTO::from)
                .collect(Collectors.toList());
    }

    public int getTotalPointByUser(Long userId) {
        List<Point> pointList = pointRepository.findByUsersId(userId);
        return pointList.stream()
                .mapToInt(Point::getPointAmount)
                .sum();
    }
    public int getAvailablePointByUser(Long userId) {
        LocalDateTime now = LocalDateTime.now();

        List<Point> points = pointRepository.findByUsersId(userId);
        return points.stream()
                .filter(p -> p.getExpiredAt() == null || p.getExpiredAt().isAfter(now))
                .mapToInt(Point::getPointAmount)
                .sum();
    }

    public void usePoint(Long userId, int amountToUse) {
        int currentPoint = getAvailablePointByUser(userId);

        if (amountToUse % 1000 != 0) {
            throw new IllegalArgumentException("포인트는 1000원 단위로만 사용할 수 있습니다.");
        }

        if (currentPoint < 3000) {
            throw new IllegalStateException("포인트는 최소 3,000원 이상일 때만 사용할 수 있습니다.");
        }

        if (currentPoint < amountToUse) {
            throw new IllegalStateException("보유 포인트가 부족합니다. 현재 잔액: " + currentPoint + "원");
        }

        Point usedPoint = Point.builder()
                .users(Users.builder().id(userId).build())
                .pointAmount(-amountToUse)
                .type(PointType.USING)
                .source(PointSource.SYSTEM)
                .description("포인트 사용")
                .expiredAt(null)
                .build();

        pointRepository.save(usedPoint);
    }
    public void userPointForOrder(Long userId, int amountToUse, Long orderId) {
        int currentPoint = getAvailablePointByUser(userId);

        if (amountToUse > 0) {
            if (amountToUse % 1000 != 0) {
                throw new IllegalArgumentException("포인트는 1000원 단위로만 사용할 수 있습니다.");
            }

            if (currentPoint < 3000) {
                throw new IllegalStateException("포인트는 최소 3,000원 이상일 때만 사용할 수 있습니다.");
            }

            if (currentPoint < amountToUse) {
                throw new IllegalStateException("보유 포인트가 부족합니다. 현재 잔액: " + currentPoint + "원");
            }
        }

            Point usedPoint = Point.builder()
                .users(Users.builder().id(userId).build())
                .pointAmount(-amountToUse)
                .type(PointType.USING)
                .source(PointSource.SYSTEM)
                .description("포인트 사용")
                .expiredAt(null)
                .relatedOrderId(orderId)
                .build();

        pointRepository.save(usedPoint);
    }

}
