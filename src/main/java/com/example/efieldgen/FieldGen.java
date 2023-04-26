package com.example.efieldgen;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * FieldGen class that constructs a GUI for calculating the electric field at different paints in space based
 * on different charge configurations. Users can either generate practice problems of their own or input data
 * for calculating solutions of existing problems.
 *
 * Work in progress to define magnetic field from different charged configurations for points in space as well!
 *
 * @author Maheen Khan
 * @version 12.0.1
 */
public class FieldGen extends Application {

    /**
     * Main method for launching FieldGen GUI program.
     * @param args String arguments that are run for GUI launch.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Integers below are defined for problem generation. Only integers are given to simplify
     * the questions the user is tasked with answering.
     */
    private int charge;
    private int radius;
    private int xCoord;
    private int yCoord;
    private int thickness;

    /**
     * Scenes defined for GUI. genProblemScene and calculatorInputScene are multi-use and defined in different
     * ways depending on the charge configuration chosen by the user.
     */
    private Scene openingScene; // welcome scene
    private Scene solveOrGenerateScene; // choose to solve or generate
    private Scene chooseChargeScene; // choose configuration
    private Scene genProblemScene; // randomly generate problem for a configuration
    private Scene correctScene; // correct screen -- prompt to return to scene 2
    private Scene calculatorInputScene; // configuration charge calculator
    private Scene solutionScene; // solution scene for pt charge
    private Scene slabSolutionScene; // solution scene for slab calculator

    /**
     * Double variables below are defined for calculating answers based on user inputs. Users are allowed to
     * input doubles and receive double answers.
     */
    private double answer;
    private double xValue;
    private double yValue;
    private double thickValue;
    private double radiusValue;
    private double chargeValue;

    /**
     * Supplementary variables for mapping out which scene to invoke using user input.
     */
    private boolean createProblem;
    private ChargeType chargeType;

    @Override
    public void start(Stage primaryStage) {
        // Create opening scene
        createOpeningScene(primaryStage);

        // Choose to solve or generate problem
        createSolveOrGenerateScene(primaryStage);

        // Choose charge config.
        createChooseConfigScene(primaryStage);

        // Create scene 5 for correct answer
        createCorrectScene(primaryStage);

        // Set initial scene and show the stage
        primaryStage.setScene(openingScene);
        primaryStage.setTitle("FieldGen");
        primaryStage.show();
    }

    /**
     * Method that prompts the user to choose which electric configuration to examine the resulting E-field of.
     * @param primaryStage Stage object that defines the program window.
     */
    private void createChooseConfigScene(Stage primaryStage) {
        Text chooseConfig = new Text("What type of source charge configuration would you like to examine?");
        chooseConfig.setTextAlignment(TextAlignment.CENTER);
        chooseConfig.setWrappingWidth(250);

        //POINT CHARGE is selected!
        Button pointChargeButton = new Button("Point Charge");
        pointChargeButton.setOnAction(e -> {
            chargeType = ChargeType.POINTCHARGE;
            if (createProblem) {
                createPtChargeProblem(primaryStage);
                primaryStage.setScene(genProblemScene);
            } else {
                createPtCalculatorInputScene(primaryStage);
                primaryStage.setScene(calculatorInputScene);
            }
        });

        //HOLLOW SPHERE is selected!
        Button hollowSphereButton = new Button("Charged Hollow Sphere");
        hollowSphereButton.setOnAction(e -> {
            chargeType = ChargeType.HOLLOWSPHERE;
            if (createProblem) {
                createHollowSphereProblem(primaryStage);
                primaryStage.setScene(genProblemScene);
            } else {
                createHollowSphereCalculatorInputScene(primaryStage);
                primaryStage.setScene(calculatorInputScene);
            }
        });

        //SOLID SPHERE is selected!
        Button solidSphereButton = new Button("Charged Solid Sphere");
        solidSphereButton.setOnAction(e -> {
            chargeType = ChargeType.SOLIDSPHERE;
            if (createProblem) {
                createSolidSphereProblem(primaryStage);
                primaryStage.setScene(genProblemScene);
            } else {
                createSolidSphereCalculatorInputScene(primaryStage);
                primaryStage.setScene(calculatorInputScene);
            }
        });

        //INFINITE LINE is selected!
        Button infiniteLineButton = new Button("Infinite Line of Charge");
        infiniteLineButton.setOnAction(e -> {
            chargeType = ChargeType.INFINITELINE;
            if (createProblem) {
                createInfiniteLineProblem(primaryStage);
                primaryStage.setScene(genProblemScene);
            } else {
                createInfiniteLineCalculatorInputScene(primaryStage);
                primaryStage.setScene(calculatorInputScene);
            }
        });

        //INFINITE SLAB is selected!
        Button infiniteSlabButton = new Button("Infinite Slab of Charge");
        infiniteSlabButton.setOnAction(e -> {
            chargeType = ChargeType.INFINITESLAB;
            if (createProblem) {
                createInfiniteSlabProblem(primaryStage);
                primaryStage.setScene(genProblemScene);
            } else {
                createInfiniteSlabCalculatorInputScene(primaryStage);
                primaryStage.setScene(calculatorInputScene);
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(solveOrGenerateScene));

        VBox layout3 = new VBox(10);
        layout3.setAlignment(Pos.CENTER);
        layout3.getChildren().addAll(chooseConfig, pointChargeButton, hollowSphereButton, solidSphereButton,
                infiniteLineButton, infiniteSlabButton, backButton);
        chooseChargeScene = new Scene(layout3, 300, 300);
    }

    /**
     * Method that creates a calculator scene for the INFINITE SLAB configuration.
     * @param primaryStage Stage object that defines the program window.
     */
    private void createInfiniteSlabCalculatorInputScene(Stage primaryStage) {
        Text ptCalcText = new Text("Assume that the infinite slab is spread across the x and z-axis and has a"
                + "uniform surface charge density. Please input the uniform surface charge density in nC/m^2, the"
                + "thickness of the slab, and the perpendicular distance away from the slab at which the electric"
                + "field should be determined.");
        ptCalcText.setWrappingWidth(250);
        ptCalcText.setTextAlignment(TextAlignment.CENTER);

        Label label1 = new Label("surface charge density in nC/m^2:");
        label1.setTextAlignment(TextAlignment.CENTER);
        TextField chargeValueField = new TextField();
        chargeValueField.setMaxWidth(150);

        Label label2 = new Label("thickness of the slab in meters:");
        label2.setTextAlignment(TextAlignment.CENTER);
        TextField thickValueField = new TextField();
        thickValueField.setMaxWidth(150);

        Label label3 = new Label("⟂ distance away from slab to find E-field:");
        label3.setTextAlignment(TextAlignment.CENTER);
        TextField xValueField = new TextField();
        xValueField.setMaxWidth(150);

        Button submitButton = new Button("Submit");

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(chooseChargeScene));

        submitButton.setOnAction(e -> {
            try {
                xValue = Double.parseDouble(xValueField.getText());
                chargeValue = Double.parseDouble(chargeValueField.getText());
                thickValue = Double.parseDouble(thickValueField.getText());

                if (thickValue / 2 <= xValue) {
                    answer = chargeValue * Math.pow(10, -9) / (2 * 8.854187817 * Math.pow(10, -12));
                } else {
                    answer = chargeValue * Math.pow(10, -9) / (2 * 8.854187817 * Math.pow(10, -12)) * (xValue
                            / (thickValue / 2));
                }

                xValueField.clear();
                chargeValueField.clear();
                thickValueField.clear();
                createSlabSolutionScene(primaryStage);
                primaryStage.setScene(slabSolutionScene);

            } catch (NumberFormatException n) {
                AlertBox.display("Number parsing error!", "Inputs must be a number. Please try again.");
            }
        });

        VBox layout6 = new VBox(10);
        layout6.getChildren().addAll(ptCalcText, label1, chargeValueField, label2, thickValueField, label3,
                xValueField, submitButton, backButton);
        layout6.setAlignment(Pos.CENTER);
        calculatorInputScene = new Scene(layout6, 300, 400);
    }

    /**
     * Method that randomly generates an INFINITE SLAB problem for the user to solve.
     * @param primaryStage Stage object that defines the program window.
     */
    private void createInfiniteSlabProblem(Stage primaryStage) {
        thickness = (int) (Math.random() * 11) + 1;
        charge = (int) (Math.random() * 21 - 10);
        xCoord = (int) (Math.random() * 21 - 10);
        // Create scene 4 to generate random problem for point charge
        Text probText = new Text(String.format("There is an infinite slab of charge with thickness %dm on the x and "
                + "z-axis with a surface charge density of η = %d nC/m^2. Find the electric field due to this slab of "
                + "charge at a point in space %d meter(s) away perpendicular to the line of charge. \nPlease input "
                + "your answer in coulombs to two decimals.", thickness, charge, xCoord));
        probText.setTextAlignment(TextAlignment.CENTER);
        probText.setWrappingWidth(250);

        TextField answerField = new TextField();
        answerField.setMaxWidth(150);

        Button submitAns = new Button("Submit");
        submitAns.setOnAction(e1 -> {
            double userAnswer;
            try {
                userAnswer = Double.parseDouble(answerField.getText());
                double answer1;
                if ((double) thickness / 2 <= xValue) {
                    answer1 = charge * Math.pow(10, -9) / (2 * 8.854187817 * Math.pow(10, -12));
                } else {
                    answer1 = charge * Math.pow(10, -9) / (2 * 8.854187817 * Math.pow(10, -12))
                            * (xCoord / ((double) thickness / 2));
                }
                answer1 = Math.round(answer1 * 100.0) / 100.0;
                System.out.println(answer1);
                boolean isAnswerCorrect = Math.abs(userAnswer - answer1) <= .1;
                if (isAnswerCorrect) {
                    primaryStage.setScene(correctScene);
                    answerField.clear();
                }
            } catch (NumberFormatException n) {
                AlertBox.display("Number parsing error!", "Input must be a number. Please try again.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(chooseChargeScene));

        VBox layout4 = new VBox(10);
        layout4.setAlignment(Pos.CENTER);
        layout4.getChildren().addAll(probText, answerField, submitAns, backButton);
        genProblemScene = new Scene(layout4, 300, 250);
    }

    /**
     * Method that creates a calculator scene for the INFINITE LINE configuration.
     * @param primaryStage Stage object that defines the program window.
     */
    private void createInfiniteLineCalculatorInputScene(Stage primaryStage) {
        Text ptCalcText = new Text("Assume that the infinite line of charge is running across the x-axis of "
                + "a coordinate system. Please input the linear charge density in nC/m and the perpendicular distance"
                + "between the infinite line of charge and the point at which you desire to find the electric field "
                + "at.");
        ptCalcText.setWrappingWidth(250);
        ptCalcText.setTextAlignment(TextAlignment.CENTER);

        Label label1 = new Label("perpendicular distance to line:");
        label1.setTextAlignment(TextAlignment.CENTER);
        TextField xValueField = new TextField();
        xValueField.setMaxWidth(150);

        Label label2 = new Label("linear charge density λ in nC:");
        label2.setTextAlignment(TextAlignment.CENTER);
        TextField chargeValueField = new TextField();
        chargeValueField.setMaxWidth(150);

        Button submitButton = new Button("Submit");

        submitButton.setOnAction(e -> {
            try {
                xValue = Double.parseDouble(xValueField.getText());
                chargeValue = Double.parseDouble(chargeValueField.getText());

                answer = (2 * 8.99 * chargeValue / xValue);
                answer = Math.round(answer * 100.0) / 100.0;
                System.out.println(answer);

                xValueField.clear();
                chargeValueField.clear();
                createLineSolutionScene(primaryStage);

                primaryStage.setScene(solutionScene);

            } catch (NumberFormatException n) {
                AlertBox.display("Number parsing error!", "Inputs must be a number. Please try again.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(chooseChargeScene));

        VBox layout6 = new VBox(10);
        layout6.getChildren().addAll(ptCalcText, label1, xValueField, label2, chargeValueField, submitButton,
                backButton);
        layout6.setAlignment(Pos.CENTER);
        calculatorInputScene = new Scene(layout6, 300, 350);
    }

    /**
     * Method that creates a solution scene for the INFINITE LINE calculator.
     * @param primaryStage Stage object that defines the program window.
     */
    private void createLineSolutionScene(Stage primaryStage) {
        Text answerMessage = new Text(String.format("The electric field induced at a perpendicular distance %.3f "
                        + "away from an infinite line of charge with a linear charge density of %.3f is %.4f.", xValue,
                chargeValue, answer));
        answerMessage.setWrappingWidth(250);
        answerMessage.setTextAlignment(TextAlignment.CENTER);
        Button returnButton = new Button("Return to the start!");
        returnButton.setOnAction(n -> primaryStage.setScene(openingScene));
        VBox layout7 = new VBox(10);

        layout7.getChildren().addAll(answerMessage, returnButton);
        layout7.setAlignment(Pos.CENTER);
        solutionScene = new Scene(layout7, 300, 250);
    }

    /**
     * Method that creates a solution scene for the INFINITE SLAB calculator.
     * @param primaryStage Stage object that defines the program window.
     */
    private void createSlabSolutionScene(Stage primaryStage) {
        Text answerMessage = new Text(String.format("The electric field induced at a perpendicular distance %.3f "
                + "meters away from an infinite slab of charge with a thickness of %.2f m and a surface charge "
                + "density of %.3f nC/m^3 is %.4f.", xValue, thickValue, chargeValue, answer));
        answerMessage.setWrappingWidth(250);
        answerMessage.setTextAlignment(TextAlignment.CENTER);
        Button returnButton = new Button("Return to the start!");
        returnButton.setOnAction(n -> primaryStage.setScene(openingScene));
        VBox layout7 = new VBox(10);
        layout7.getChildren().addAll(answerMessage, returnButton);
        layout7.setAlignment(Pos.CENTER);
        slabSolutionScene = new Scene(layout7, 300, 250);
    }

    /**
     * Method that randomly generates an INFINITE LINE problem for the user to solve.
     * @param primaryStage Stage object that defines the program window.
     */
    private void createInfiniteLineProblem(Stage primaryStage) {
        radius = (int) (Math.random() * 11) + 1;
        charge = (int) (Math.random() * 21 - 10);
        // Create scene 4 to generate random problem for point charge
        Text probText = new Text(String.format("There is an infinite line of charge on the x-axis with a linear "
                + "charge density of λ = %d nC/m. Find the electric field due to this line of charge at a point in "
                + "space %d meter(s) away perpendicular to the line of charge. \nPlease input your answer in coulombs "
                + "to two decimals.", charge, radius));
        probText.setTextAlignment(TextAlignment.CENTER);
        probText.setWrappingWidth(250);

        TextField answerField = new TextField();
        answerField.setMaxWidth(150);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(chooseChargeScene));

        Button submitAns = new Button("Submit");
        submitAns.setOnAction(e1 -> {
            double userAnswer;
            try {
                userAnswer = Double.parseDouble(answerField.getText());
                double answer1 = (2 * 8.99 * charge / radius);
                answer1 = Math.round(answer1 * 100.0) / 100.0;
                System.out.println(answer1);
                boolean isAnswerCorrect = Math.abs(userAnswer - answer1) <= .1;
                if (isAnswerCorrect) {
                    primaryStage.setScene(correctScene);
                    answerField.clear();
                }
            } catch (NumberFormatException n) {
                AlertBox.display("Number parsing error!", "Input must be a number. Please try again.");
            }
        });

        VBox layout4 = new VBox(10);
        layout4.setAlignment(Pos.CENTER);
        layout4.getChildren().addAll(probText, answerField, submitAns, backButton);
        genProblemScene = new Scene(layout4, 300, 250);
    }

    /**
     * Method that creates a calculator scene for the SOLID SPHERE configuration.
     * @param primaryStage Stage object that defines the program window.
     */
    private void createSolidSphereCalculatorInputScene(Stage primaryStage) {
        Text ptCalcText = new Text("Assume that the charged solid sphere is centered at the origin of your "
                + "coordinate system. Please define your x value and y value for the point in space you'd like to "
                + "determine the E-field for. Also provide the uniform total charge of the charged sphere in "
                + "nanocoulombs and the radius of the sphere in meters. Note that the sphere will assume to have a "
                + "uniform volume charge distribution.");
        ptCalcText.setWrappingWidth(250);
        ptCalcText.setTextAlignment(TextAlignment.CENTER);

        Label label1 = new Label("x value:");
        label1.setTextAlignment(TextAlignment.CENTER);
        TextField xValueField = new TextField();
        xValueField.setMaxWidth(150);

        Label label2 = new Label("y value:");
        label2.setTextAlignment(TextAlignment.CENTER);
        TextField yValueField = new TextField();
        yValueField.setMaxWidth(150);

        Label label3 = new Label("charge in nanocoulombs:");
        label3.setTextAlignment(TextAlignment.CENTER);
        TextField chargeValueField = new TextField();
        chargeValueField.setMaxWidth(150);

        Label label4 = new Label("radius of sphere in meters:");
        label4.setTextAlignment(TextAlignment.CENTER);
        TextField radiusValueField = new TextField();
        radiusValueField.setMaxWidth(150);

        Button submitButton = new Button("Submit");

        submitButton.setOnAction(e -> {
            try {
                xValue = Double.parseDouble(xValueField.getText());
                yValue = Double.parseDouble(yValueField.getText());
                chargeValue = Double.parseDouble(chargeValueField.getText());
                radiusValue = Double.parseDouble(radiusValueField.getText());
                if (distanceFormula(xValue, yValue) >= radiusValue) {
                    answer = pointFieldSolver(distanceFormula(xValue, yValue), chargeValue);
                } else {
                    answer = (8.99 * Math.pow(10, 9)) * charge * distanceFormula(xCoord, yCoord)
                            / (Math.pow(radius, 3));
                    answer = (double) Math.round(answer * 1000.0) / 1000;
                }
                xValueField.clear();
                yValueField.clear();
                chargeValueField.clear();
                radiusValueField.clear();
                createSolutionScene(primaryStage);

                primaryStage.setScene(solutionScene);

            } catch (NumberFormatException n) {
                AlertBox.display("Number parsing error!", "Inputs must be a number. Please try again.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(chooseChargeScene));

        VBox layout6 = new VBox(10);
        layout6.getChildren().addAll(ptCalcText, label1, xValueField, label2, yValueField, label3, chargeValueField,
                label4, radiusValueField, submitButton, backButton);
        layout6.setAlignment(Pos.CENTER);
        calculatorInputScene = new Scene(layout6, 300, 500);
    }

    /**
     * Method that randomly generates a POINT CHARGE problem for the user to solve.
     * @param primaryStage Stage object that defines the program window.
     */
    private void createPtChargeProblem(Stage primaryStage) {
        xCoord = (int) (Math.random() * 21 - 10);
        yCoord = (int) (Math.random() * 21 - 10);
        charge = (int) (Math.random() * 21 - 10);
        // Create scene 4 to generate random problem for point charge
        Text probText = new Text(String.format("Assume the point charge is located at the origin of a "
                + "coordinate system with a charge of %d nC. Find the resulting electric field due to "
                + "this point charge at the point (%d, %d).\nPlease input your answer in coulombs to two "
                + "decimals.", charge, xCoord, yCoord));
        probText.setTextAlignment(TextAlignment.CENTER);
        probText.setWrappingWidth(250);

        TextField answerField = new TextField();
        answerField.setMaxWidth(150);

        Button submitAns = new Button("Submit");
        submitAns.setOnAction(e1 -> {
            double userAnswer;
            try {
                userAnswer = Double.parseDouble(answerField.getText());
                double answer1 = pointFieldSolver(distanceFormula(xCoord, yCoord), charge);
                System.out.println(answer1);
                boolean isAnswerCorrect = Math.abs(userAnswer - answer1) <= .1;
                if (isAnswerCorrect) {
                    primaryStage.setScene(correctScene);
                    answerField.clear();
                }
            } catch (NumberFormatException n) {
                AlertBox.display("Number parsing error!", "Input must be a number. Please try again.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(chooseChargeScene));

        VBox layout4 = new VBox(10);
        layout4.setAlignment(Pos.CENTER);
        layout4.getChildren().addAll(probText, answerField, submitAns, backButton);
        genProblemScene = new Scene(layout4, 300, 250);
    }

    /**
     * Method that randomly generates a HOLLOW SPHERE problem for the user to solve.
     * @param primaryStage Stage object that defines the program window.
     */
    private void createHollowSphereProblem(Stage primaryStage) {
        xCoord = (int) (Math.random() * 21 - 10);
        yCoord = (int) (Math.random() * 21 - 10);
        radius = (int) (Math.random() * 5 + 1);
        charge = (int) (Math.random() * 21 - 10);
        // Create scene8 to generate random problem for hollow spherical charge
        Text probText = new Text(String.format("Assume a hollow spherical charge with a radius of %d is located at "
                + "the origin of a coordinate system with a charge of %d nC. The charge is uniformly distributed "
                + "amongst the thin spherical shell. Find the resulting electric field due to this infinitely thin "
                + "spherical shell at the point (%d, %d).\nPlease input your answer in coulombs to two decimals.",
                radius, charge, xCoord, yCoord));
        probText.setTextAlignment(TextAlignment.CENTER);
        probText.setWrappingWidth(250);

        TextField answerField = new TextField();
        answerField.setMaxWidth(150);

        Button submitAns = new Button("Submit");
        submitAns.setOnAction(o -> {
            double userAnswer;
            try {
                userAnswer = Double.parseDouble(answerField.getText());
                if (distanceFormula(xCoord, yCoord) >= radius) {
                    answer = pointFieldSolver(distanceFormula(xCoord, yCoord), charge);
                } else {
                    answer = 0;
                }
                System.out.println(answer);
                boolean isAnswerCorrect = Math.abs(userAnswer - answer) <= .1;
                if (isAnswerCorrect) {
                    primaryStage.setScene(correctScene);
                    answerField.clear();
                }
            } catch (NumberFormatException n) {
                AlertBox.display("Number parsing error!", "Input must be a number. Please try again.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(chooseChargeScene));

        VBox layout4 = new VBox(10);
        layout4.setAlignment(Pos.CENTER);
        layout4.getChildren().addAll(probText, answerField, submitAns, backButton);
        genProblemScene = new Scene(layout4, 300, 300);
    }

    /**
     * Method that randomly generates a SOLID SPHERE problem for the user to solve.
     * @param primaryStage Stage object that defines the program window.
     */
    private void createSolidSphereProblem(Stage primaryStage) {
        xCoord = (int) (Math.random() * 21 - 10);
        yCoord = (int) (Math.random() * 21 - 10);
        radius = (int) (Math.random() * 5 + 1);
        charge = (int) (Math.random() * 21 - 10);

        Text probText = new Text(String.format("Assume a solid spherical charge with a radius of %d is "
                + "located at the origin of a coordinate system with a charge of %d nC. The sphere carries a "
                + "uniform volume charge density. Find the resulting electric field due to this infinitely "
                + "thin spherical shell at the point (%d, %d).\nPlease input your answer in coulombs to two "
                + "decimals.", radius, charge, xCoord, yCoord));
        probText.setTextAlignment(TextAlignment.CENTER);
        probText.setWrappingWidth(250);

        TextField answerField = new TextField();
        answerField.setMaxWidth(150);

        Button submitAns = new Button("Submit");
        submitAns.setOnAction(o -> {
            double userAnswer;
            try {
                userAnswer = Double.parseDouble(answerField.getText());
                if (distanceFormula(xCoord, yCoord) >= radius) {
                    answer = pointFieldSolver(distanceFormula(xCoord, yCoord), charge);
                } else {
                    answer = (8.99 * Math.pow(10, 9)) * charge * distanceFormula(xCoord, yCoord)
                            / (Math.pow(radius, 3));
                    answer = (double) Math.round(answer * 1000.0) / 1000;
                }
                System.out.println(answer);
                boolean isAnswerCorrect = Math.abs(userAnswer - answer) <= .1;
                if (isAnswerCorrect) {
                    primaryStage.setScene(correctScene);
                    answerField.clear();
                }
            } catch (NumberFormatException n) {
                AlertBox.display("Number parsing error!", "Input must be a number. "
                        + "Please try again.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(chooseChargeScene));

        VBox layout4 = new VBox(10);
        layout4.setAlignment(Pos.CENTER);
        layout4.getChildren().addAll(probText, answerField, submitAns, backButton);
        genProblemScene = new Scene(layout4, 300, 250);
    }

    /**
     * Method that creates the OPENING SCENE that launches upon start.
     * @param primaryStage Stage object that defines the program window.
     */
    private void createOpeningScene(Stage primaryStage) {
        Text startText = new Text("Welcome to FieldGen, a student-created platform for practicing "
                + "Electricity & Magnetism practice problems!");
        startText.setTextAlignment(TextAlignment.CENTER);
        startText.setWrappingWidth(250);

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(e -> primaryStage.setScene(solveOrGenerateScene));

        Text authorText = new Text("\n\nDeveloped by Maheen Khan.\nWIP to support variable charge densities and "
                + "magnetic field calculations.");
        authorText.setTextAlignment(TextAlignment.CENTER);
        authorText.setWrappingWidth(250);

        VBox layout1 = new VBox(10);
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(startText, continueButton, authorText);

        openingScene = new Scene(layout1, 300, 250);
    }

    /**
     * Method that prompts the user to choose whether they desire to solve or generate
     * an E-field problem.
     * @param primaryStage Stage object that defines the program window.
     */
    private void createSolveOrGenerateScene(Stage primaryStage) {
        Text chooseType = new Text("Please choose whether you'd like to generate an e-field problem "
                + "or solve an existing problem.");
        chooseType.setTextAlignment(TextAlignment.CENTER);
        chooseType.setWrappingWidth(250);

        Button generateButton = new Button("Generate Problem");
        generateButton.setOnAction(e -> {
            createProblem = true;
            primaryStage.setScene(chooseChargeScene);
        });

        Button solveButton = new Button("Solve Existing Problem");
        solveButton.setOnAction(e -> {
            createProblem = false;
            primaryStage.setScene(chooseChargeScene);
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(openingScene));

        VBox layout2 = new VBox(10);
        layout2.setAlignment(Pos.CENTER);
        layout2.getChildren().addAll(chooseType, generateButton, solveButton, backButton);

        solveOrGenerateScene = new Scene(layout2, 300, 250);
    }

    /**
     * Method that creates a CORRECT SCENE when the user inputs the correct answer to a
     * randomly generated problem.
     * @param primaryStage Stage object that defines the program window.
     */
    private void createCorrectScene(Stage primaryStage) {
        Text correctText = new Text("Great work, you've got it!");
        Button keepPracticing = new Button("Keep practicing!");
        keepPracticing.setOnAction(e -> primaryStage.setScene(solveOrGenerateScene));
        VBox layout5 = new VBox(10);
        layout5.setAlignment(Pos.CENTER);
        layout5.getChildren().addAll(correctText, keepPracticing);
        correctScene = new Scene(layout5, 300, 250);
    }

    /**
     * Method that creates a calculator scene for the POINT CHARGE configuration.
     * @param primaryStage Stage object that defines the program window.
     */
    private void createPtCalculatorInputScene(Stage primaryStage) {
        Text ptCalcText = new Text("Assume that the point charge is centered at the origin of your coordinate "
                + "system. Please define your x value and y value for the point in space you'd like to determine the"
                + " E-field for. Also provide the charge of the point charge in nanocoulombs.");
        ptCalcText.setWrappingWidth(250);
        ptCalcText.setTextAlignment(TextAlignment.CENTER);

        Label label1 = new Label("x value (meters):");
        label1.setTextAlignment(TextAlignment.CENTER);
        TextField xValueField = new TextField();
        xValueField.setMaxWidth(150);

        Label label2 = new Label("y value (meters):");
        label2.setTextAlignment(TextAlignment.CENTER);
        TextField yValueField = new TextField();
        yValueField.setMaxWidth(150);

        Label label3 = new Label("charge in nanocoulombs:");
        label3.setTextAlignment(TextAlignment.CENTER);
        TextField chargeValueField = new TextField();
        chargeValueField.setMaxWidth(150);
        Button submitButton = new Button("Submit");

        submitButton.setOnAction(e -> {
            try {
                xValue = Double.parseDouble(xValueField.getText());
                yValue = Double.parseDouble(yValueField.getText());
                chargeValue = Double.parseDouble(chargeValueField.getText());
                answer = pointFieldSolver(distanceFormula(xValue, yValue), chargeValue);
                xValueField.clear();
                yValueField.clear();
                chargeValueField.clear();
                createSolutionScene(primaryStage);
                // call scene 7 with answer
                primaryStage.setScene(solutionScene);

            } catch (NumberFormatException n) {
                AlertBox.display("Number parsing error!", "Inputs must be a number. Please try again.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(chooseChargeScene));

        VBox layout6 = new VBox(10);
        layout6.getChildren().addAll(ptCalcText, label1, xValueField, label2, yValueField, label3, chargeValueField,
                submitButton, backButton);
        layout6.setAlignment(Pos.CENTER);
        calculatorInputScene = new Scene(layout6, 300, 400);
    }

    /**
     * Method that creates a calculator scene for the HOLLOW SPHERE configuration.
     * @param primaryStage Stage object that defines the program window.
     */
    private void createHollowSphereCalculatorInputScene(Stage primaryStage) {
        Text ptCalcText = new Text("Assume that the charged thin spherical shell is centered at the origin of your "
                + "coordinate system. Please define your x value and y value for the point in space you'd like to "
                + "determine the E-field for. Also provide the uniform total charge of the spherical shell in "
                + "nanocoulombs and the radius of the sphere in meters.");
        ptCalcText.setWrappingWidth(250);
        ptCalcText.setTextAlignment(TextAlignment.CENTER);

        Label label1 = new Label("x value:");
        label1.setTextAlignment(TextAlignment.CENTER);
        TextField xValueField = new TextField();
        xValueField.setMaxWidth(150);

        Label label2 = new Label("y value:");
        label2.setTextAlignment(TextAlignment.CENTER);
        TextField yValueField = new TextField();
        yValueField.setMaxWidth(150);

        Label label3 = new Label("charge in nanocoulombs:");
        label3.setTextAlignment(TextAlignment.CENTER);
        TextField chargeValueField = new TextField();
        chargeValueField.setMaxWidth(150);

        Label label4 = new Label("radius of shell in meters:");
        label4.setTextAlignment(TextAlignment.CENTER);
        TextField radiusValueField = new TextField();
        radiusValueField.setMaxWidth(150);

        Button submitButton = new Button("Submit");

        submitButton.setOnAction(e -> {
            try {
                xValue = Double.parseDouble(xValueField.getText());
                yValue = Double.parseDouble(yValueField.getText());
                chargeValue = Double.parseDouble(chargeValueField.getText());
                radiusValue = Double.parseDouble(radiusValueField.getText());
                if (distanceFormula(xValue, yValue) >= radiusValue) {
                    answer = pointFieldSolver(distanceFormula(xValue, yValue), chargeValue);
                } else {
                    answer = 0;
                }
                xValueField.clear();
                yValueField.clear();
                chargeValueField.clear();
                radiusValueField.clear();
                createSolutionScene(primaryStage);

                primaryStage.setScene(solutionScene);

            } catch (NumberFormatException n) {
                AlertBox.display("Number parsing error!", "Inputs must be a number. Please try again.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(chooseChargeScene));

        VBox layout6 = new VBox(10);
        layout6.getChildren().addAll(ptCalcText, label1, xValueField, label2, yValueField, label3, chargeValueField,
                label4, radiusValueField, submitButton, backButton);
        layout6.setAlignment(Pos.CENTER);
        calculatorInputScene = new Scene(layout6, 300, 450);

    }

    /**
     * Method that creates a solution scene upon submission of inputs into the POINT CHARGE, HOLLOW SPHERE,
     * and SOLID SPHERE calculators.
     * @param primaryStage Stage object that defines the program window.
     */
    private void createSolutionScene(Stage primaryStage) {
        Text answerMessage = new Text(String.format("The electric field induced at the point (%.2f, %.2f) by a "
                + "%.2f coulomb charge located at the origin is: %.4f V/m.", xValue, yValue, chargeValue, answer));
        answerMessage.setWrappingWidth(250);
        answerMessage.setTextAlignment(TextAlignment.CENTER);
        Button returnButton = new Button("Return to the start!");
        returnButton.setOnAction(n -> primaryStage.setScene(openingScene));
        VBox layout7 = new VBox(10);
        layout7.getChildren().addAll(answerMessage, returnButton);
        layout7.setAlignment(Pos.CENTER);
        solutionScene = new Scene(layout7, 300, 250);
    }

    /**
     * Method that computes the distance from (0, 0) to a specified point in space.
     * @param i x-coordinate for input into distance formula.
     * @param j y-coordinate for input into distance formula.
     * @return double representing computed distance.
     */
    private double distanceFormula(double i, double j) {
        return Math.sqrt(i * i + j * j);
    }

    /**
     * Method that computes the electric field due to a point charge in space.
     * @param distance double representing the distance between selected point in space and point charge at origin.
     * @param ptCharge double representing the point charge in nanocoulombs.
     * @return double representing the electric field generated by the point charge at the selected point in space.
     */
    private double pointFieldSolver(double distance, double ptCharge) {
        ptCharge *= Math.pow(10, -9);
        double k = 8.99 * Math.pow(10, 9);
        double ptAnswer = k * ptCharge / (Math.pow(distance, 2));
        return Math.round(ptAnswer * 100.0) / 100.0;
    }

}