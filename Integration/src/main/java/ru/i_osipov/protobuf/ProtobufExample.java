package ru.i_osipov.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import ru.i_osipov.academy.AcademyProtos;

public class ProtobufExample {

    public static void main(String[] args) {
        AcademyProtos.Group group = AcademyProtos.Group.newBuilder()
                .setName("Math")
                .addStudent(AcademyProtos.Student.newBuilder()
                        .setId(123456)
                        .addEmail("student@example.com")
                        .addEmail("student2@example.com")
                        .setGender(AcademyProtos.Student.Gender.FEMALE)
                        .setName("Ivanova")
                        .build())
                .addStudent(AcademyProtos.Student.newBuilder()
                        .setId(123457)
                        .addEmail("student3@example.com")
                        .addEmail("student4@example.com")
                        .setGender(AcademyProtos.Student.Gender.MALE)
                        .setName("Ivanov")
                        .build())
                .build();

        byte[] serializedGroup = group.toByteArray();

        try {
            AcademyProtos.Group unserialinedGroup = AcademyProtos.Group.parseFrom(serializedGroup);

            System.out.println(unserialinedGroup);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }


    }

}
