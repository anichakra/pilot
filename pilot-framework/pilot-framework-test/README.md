# Introduction

This is a further opinionated unit test framework on top of springboot-test framework. The objective is to simplify the way a test case is written to test a ReST/JSON API using declarative programming style. This framework can be used with JUnit.

# Design
Each test class should pertain to one controller class which implements a set of related APIs. Each test class should be annotated with framework-test's @MicroserviceTest and JUnit's @RunWith(MicroserviceTestRunner)