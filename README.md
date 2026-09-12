# Idempotent Order Service

A Spring Boot backend service that demonstrates a reliable order creation workflow with idempotency handling, database consistency, and concurrency control.

---

## Overview

In real-world systems, creating an order is not always a simple operation.

Duplicate requests can happen because of:

- Users clicking the submit button multiple times
- Network retries
- Client timeout and retry mechanisms
- Distributed system failures

Without proper handling, the same request may create multiple orders and cause data inconsistency.

This project implements an idempotent order creation flow to guarantee that repeated requests create only one order.

---

# Key Features

- Idempotent order creation using `Idempotency-Key`
- Prevent duplicate orders caused by repeated client requests
- Database-level consistency using unique constraints
- Prevent multiple active orders for the same user
- Transactional order and order item creation
- Database schema management using Flyway
- RESTful API implementation with Spring Boot
- Unit testing for critical business scenarios

---

# Idempotency Flow

The client sends a unique idempotency key in the request header:

```http
POST /api/orders

Idempotency-Key: 550e8400-e29b-41d4-a716-446655440000