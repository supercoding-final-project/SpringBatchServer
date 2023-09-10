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
	NETWORK("Network", 4L),
	C_PLUS_PLUS("C++", 5L),
	AWS("AWS", 6L),
	MVC("MVC", 7L),
	SPRING_BOOT("Spring Boot", 8L),
	JPA("JPA", 9L),
	OOP("객체지향", 10L),
	SPRING_JDBC("Spring-jdbc", 11L),
	NODE_JS("Node.js", 12L),
	FLUTTER("Flutter", 13L),
	C_SHOP("C#", 14L),
	ANDROID("Android", 15L),
	SQL("SQL", 16L),
	DBMS_RDBMS("DBMS/RDBMS", 17L),
	MYSQL("MYSQL", 18L),
	MONGODB("MongoDB", 19L),
	PHP("PHP", 20L),
	EXPRESS("Express", 21L),
	JAVA_SCRIPT("JavaScript", 22L),
	VUE_JS("Vue.js", 23L),
	GIT("Git", 24L),
	SOCKET_IO("Socket.io", 25L),
	HTML_CSS("HTML/CSS", 26L),
	REACT("React", 27L),
	JQUERY("jQuery", 28L),
	DOCKER("Docker", 29L),
	GRAPH_QL("GraphQL", 30L),
	DJANGO("Django", 31L),
	NEXT_JS("Next.js", 32L),
	TYPE_SCRIPT("TypeScript", 33L),
	FIREBASE("FireBase", 34L),
	GSAP("gsap", 35L),
	REDUX("Redux", 36L),
	WEBPACK("Webpack", 37L),
	SASS("Sass", 38L),
	KAFKA("Kafka", 39L),
	REDIS("Redis", 40L),
	LINUX("Linux", 41L),
	BOOTSTRAP("Bootstrap", 42L),
	IOS("iOS", 43L),
	SWIFT("Swift", 44L),
	REACT_NATIVE("React Native", 45L),
	SWIFT_UI("SwiftUI", 46L),
	DART("dart", 47L),
	MVVM("MVVM", 48L),
	DOT_NET(".NET", 49L),
	APOLLO("Apollo", 50L),
	POSTGRE_SQL("PostgreSQL", 51L),
	ORACLE("Oracle", 52L);

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
