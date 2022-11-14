package net.onurozcelik.archunitdemo;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import net.onurozcelik.archunitdemo.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchUnitDemoApplicationArchitectureTest {

    private static JavaClasses importedClasses;

    @BeforeAll
    public static void beforeAll() {
        List<ImportOption> importOptions = new ArrayList<>();
        importOptions.add(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS);
        importedClasses = new ClassFileImporter(importOptions).importPackages("net.onurozcelik.archunitdemo");
    }

    @Test
    void architectureRule1Test() {
        ArchRule rule = noClasses().that().resideInAPackage("..controllers..").should()
                .accessClassesThat()
                .resideInAnyPackage("..repositories..");
        rule.check(importedClasses);
    }

    @Test
    void architectureRule2Test() {
        ArchRule rule = noClasses().that().haveNameMatching(".*Repository")
                .should().dependOnClassesThat().haveNameMatching(".*Service");
        rule.check(importedClasses);
    }

    @Test
    void architectureRule3Test() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("Service")
                .should().resideInAPackage("..services..");
        rule.check(importedClasses);
    }

    @Test
    void architectureRule4Test() {
        ArchRule rule = classes().that().implement(UserService.class)
                .should().haveSimpleNameContaining("UserService")
                .andShould().haveSimpleNameEndingWith("Impl");
        rule.check(importedClasses);
    }

    @Test
    void architectureRule5Test() {
        ArchRule rule = classes().that().areAssignableTo(UserService.class)
                .and().haveSimpleNameEndingWith("Impl").should().beAnnotatedWith(Service.class);
        rule.check(importedClasses);
    }

    @Test
    void architectureRule6Test() {
        layeredArchitecture().consideringAllDependencies()
                .layer("Controllers").definedBy("..controllers..")
                .layer("Services").definedBy("..services..")
                .layer("Repositories").definedBy("..repositories..")
                .layer("Entities").definedBy("..entities..")
                .whereLayer("Controllers").mayNotBeAccessedByAnyLayer()
                .whereLayer("Services").mayOnlyBeAccessedByLayers("Controllers")
                .whereLayer("Repositories").mayOnlyBeAccessedByLayers("Services")
                .whereLayer("Entities").mayOnlyBeAccessedByLayers("Repositories");
    }
}
