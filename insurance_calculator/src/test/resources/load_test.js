import http from 'k6/http';
import { check } from 'k6';

// --- Конфигурация теста ---
export const options = {
  // Executor для ступенчатой нагрузки, контролирующий частоту запросов (RPS)
  executor: 'ramping-arrival-rate',

  // Количество виртуальных пользователей, которые будут выполнять тест.
  // k6 будет использовать этих пользователей для достижения целевого RPS.
  preAllocatedVUs: 3,

  // Максимальное количество пользователей на случай, если 3-х будет недостаточно
  // для достижения пиковой нагрузки в 20 RPS.
  maxVUs: 50,

  // Определение этапов (ступеней) теста
  stages: [
    // Этап 1: Плавный старт до 1 RPS в течение 10 секунд
    { duration: '10s', target: 1 },
    // Этап 2: Удержание нагрузки в 1 RPS в течение 20 секунд
    { duration: '20s', target: 1 },

    // Этап 3: Плавное увеличение до 10 RPS в течение 30 секунд
    { duration: '30s', target: 10 },
    // Этап 4: Удержание нагрузки в 10 RPS в течение 1 минуты
    { duration: '1m', target: 10 },

    // Этап 5: Плавное увеличение до 20 RPS в течение 30 секунд
    { duration: '30s', target: 20 },
    // Этап 6: Удержание пиковой нагрузки в 20 RPS в течение 1 минуты
    { duration: '1m', target: 20 },

    // Этап 7: Плавное снижение нагрузки до 0 для завершения теста
    { duration: '20s', target: 0 },
  ],
};

// --- Основная функция теста ---
export default function () {
  // URL вашего локального API
  const url = 'http://localhost:8080/insurance/travel/api/v2/';

  // Тело POST-запроса
  const payload = JSON.stringify({
    agreementDateFrom: '2026-05-25',
    agreementDateTo: '2026-05-29',
    country: 'SPAIN',
    selectedRisks: ['TRAVEL_MEDICAL', 'TRAVEL_CANCELLATION', 'TRAVEL_EVACUATION'],
    persons: [
      {
        personFirstName: 'Vasja',
        personLastName: 'Pupkin',
        personCode: 'code',
        personBirthDate: '2000-05-29',
        medicalRiskLimitLevel: 'LEVEL_10000',
      },
      {
        personFirstName: 'Petja',
        personLastName: 'Pupkin',
        personCode: 'code',
        personBirthDate: '1950-02-28',
        medicalRiskLimitLevel: 'LEVEL_20000',
      },
    ],
  });

  // Заголовки запроса, указывающие, что мы отправляем JSON
  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  // Отправка POST-запроса
  const res = http.post(url, payload, params);

  // Проверка ответа: убеждаемся, что статус код равен 200 (OK)
  check(res, {
    'status is 200': (r) => r.status === 200,
  });
}