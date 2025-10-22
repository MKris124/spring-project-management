export class TaskDto {
    id!: number;
    title!: string;
    type!: string; // E.g. 'FELADAT', 'MEETING' - lehet enum
    status!: string; // E.g. 'FOLYAMATBAN', 'TELJESÍTVE', 'ELHALASZTVA' - lehet enum
    dueDate!: Date;
    employeeId!: number; // A felelős felhasználó ID-je
    employeeName!: string; // A felelős felhasználó neve
    projectId!: number; // A projekt, amelyhez tartozik a feladat
  }
  