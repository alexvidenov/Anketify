import { Question } from "../../../models/Question";
import { FormUserAnswers } from "../../../models/Form";
import {
  Card,
  Checkbox,
  Divider,
  Grid,
  List,
  ListItem,
  ListItemText,
  makeStyles,
  FormControl,
  FormLabel,
  RadioGroup,
  Radio,
  FormControlLabel,
  Button,
  Typography,
} from "@material-ui/core";
import { UserAnswer } from "../../../models/UserAnswer";
import { useState } from "react";

interface Props {
  formId?: number;
  questions: Question[];
  onFinishedAnswering: ((userAnswers: UserAnswer[]) => void) | null;
  userVotes?: FormUserAnswers;
}

const useStyles = makeStyles((theme) => ({
  columnCard: {
    flexDirection: "column",
  },
  row: {
    flexDirection: "row",
  },
  padding: {
    padding: 30,
  },
}));

const QuestionsList = ({
  formId,
  questions,
  onFinishedAnswering,
  userVotes,
}: Props) => {
  const classes = useStyles();

  const [userAnswers, setUserAnswers] = useState<UserAnswer[]>([]);

  const handleNewUserAnswer = (answer: UserAnswer) => {
    setUserAnswers([...userAnswers, answer]);
  };

  const handleNewRadioUserAnswer = (answer: UserAnswer) => {
    const newUserAnswers = userAnswers.filter((element) => {
      return (
        element.questionId !== answer.questionId &&
        element.index !== answer.index
      );
    });
    setUserAnswers([...newUserAnswers, answer]);
  };

  const handleRemovedUserAnswer = (answer: UserAnswer) => {
    const newUserAnswers = userAnswers.filter((element) => {
      return (
        element.questionId !== answer.questionId &&
        element.index !== answer.index
      );
    });
    setUserAnswers(newUserAnswers);
  };

  const getAggregatedAnswersForAnswerWithId = (
    id?: number
  ): number | undefined => {
    if (userVotes !== undefined && id !== undefined) {
      return userVotes[id];
    } else return 0;
  };

  return (
    <>
      <List className={classes.padding}>
        {questions?.map((question, index) => (
          <>
            <Card raised>
              <ListItem className={classes.columnCard} key={index.toString()}>
                <FormControl component="fieldset">
                  <FormLabel component="legend">
                    {question.description}
                  </FormLabel>
                </FormControl>
                {onFinishedAnswering === null && (
                  <Grid
                    container
                    alignItems="center"
                    spacing={10}
                    justify="center"
                  >
                    <Grid item>
                      <ListItemText primary={"Is optional"} />
                      <Checkbox checked={question.optional}></Checkbox>
                      <Divider />
                    </Grid>
                    <Grid item>
                      <ListItemText primary={"Select more than one?"} />
                      <Checkbox
                        checked={question.canSelectMoreThanOne}
                      ></Checkbox>
                      <Divider variant="middle" />
                    </Grid>
                  </Grid>
                )}
                <RadioGroup>
                  {question.answers.map((answer, index) => (
                    <>
                      <FormControlLabel
                        value={answer.description}
                        control={
                          question.canSelectMoreThanOne ? (
                            onFinishedAnswering === null ? (
                              <Checkbox disabled />
                            ) : (
                              <Checkbox
                                onChange={(event) => {
                                  const answer = {
                                    index: index,
                                    questionId: question.id,
                                    formId: formId,
                                  };
                                  if (event.target.checked === true) {
                                    handleNewUserAnswer(answer);
                                  } else {
                                    handleRemovedUserAnswer(answer);
                                  }
                                }}
                              />
                            )
                          ) : onFinishedAnswering === null ? (
                            <Radio disabled />
                          ) : (
                            <Radio
                              onChange={(event) => {
                                const answer = {
                                  index: index,
                                  questionId: question.id,
                                  formId: formId,
                                };
                                if (event.target.checked === true) {
                                  handleNewRadioUserAnswer(answer);
                                }
                              }}
                              value={answer.description}
                            />
                          )
                        }
                        label={answer.description}
                      />
                      {userVotes !== undefined && (
                        <Typography>
                          Votes:{" "}
                          {getAggregatedAnswersForAnswerWithId(answer.id)}
                        </Typography>
                      )}
                    </>
                  ))}
                </RadioGroup>
              </ListItem>
            </Card>
            <Divider />
          </>
        ))}
      </List>
      {onFinishedAnswering !== null && (
        <Button
          onClick={() => {
            onFinishedAnswering(userAnswers);
          }}
        >
          SUBMIT
        </Button>
      )}
    </>
  );
};

export default QuestionsList;
