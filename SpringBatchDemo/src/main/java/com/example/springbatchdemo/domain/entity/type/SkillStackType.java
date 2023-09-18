package com.example.springbatchdemo.domain.entity.type;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SkillStackType {
	SPRING("Spring", 1L),
	PYTHON("Python", 2L),
	KOTLIN("Kotlin", 3L),
	C_PLUS_PLUS("C++", 4L),
	SPRING_BOOT("Spring Boot", 5L),
	JPA("Jpa", 6L),
	SPRING_JDBC("Spring JDBC", 7L),
	NODE_JS("Node.js", 8L),
	SQL("SQL", 9L),
	DBMS_RDBMS("DBMS_RDBMS", 10L),
	REDIS("Redis", 11L),
	POSTGRE_SQL("PostgreSQL", 12L),
	REACT("React", 13L),
	REACT_NATIVE("React Native", 14L),
	VUE("Vue", 15L),
	TYPE_SCRIPT("TypeScript", 16L),
	NEXT_JS("Next.js", 17L),
	SVELTE("Svelte", 18L),
	KUBERNETES("Kubernetes", 19L),
	JENKINS("Jenkins", 20L),
	GITHUB_ACTION("GitHub Actions", 21L),
	HARBOR("Harbor", 22L),
	JAVA("Java", 23L),
	JAVA_SCRIPT("JavaScript", 24L),
	RUBY("Ruby", 25L),
	AWS("Amazon Web Services (AWS)", 26L),
	GO("Go", 27L),
	RUST("Rust", 28L),
	KAFKA("Apache Kafka", 29L),
	MYSQL("MySQL", 30L),
	MONGODB("MongoDB", 31L),
	MARIA("MariaDB", 32L),
	ORACLE("Oracle", 33L),
	MSA("Microservices Architecture (MSA)", 34L),
	DOCKER("Docker", 35L);

	private final String skillStackName;
	private final Long skillStackCode;

	// 커스텀 에러 Refactoring 필요
	public static SkillStackType findBySkillStackType (String inputSkillStack){
		return Arrays.stream(SkillStackType.values())
				.filter(skillStackType -> inputSkillStack.equalsIgnoreCase(skillStackType.skillStackName))
				.findFirst()
				.orElse(null);
	}
}
