# Idempotent Order Service

A Spring Boot backend service that demonstrates a reliable order creation flow with idempotency handling, database consistency, and concurrency control.

## Overview

In real-world systems, creating an order is not always a simple operation.

Duplicate requests can happen because of:

- Users clicking the submit button multiple times
- Network retries
- Client timeout and retry mechanisms
- Distributed system failures

Without proper handling, the same request may create multiple orders.

This project implements an idempotent order creation mechanism to guarantee that each request creates only one order.

---

# Features

- Idempotent order creation using `Idempotency-Key`
- Prevent duplicate orders caused by repeated requests
- Database-level consistency using unique constraints
- Allow only one `OPEN` order per user
- Transactional order and order item creation
- Database migration management using Flyway
- REST API implementation with Spring Boot

---

# Idempotency Flow

The client sends a unique idempotency key in the request header:

```http
POST /api/orders

Idempotency-Key: 550e8400-e29b-41d4-a716-446655440000