import { TaskDto } from "./task.dto";
export  class ProjectDto {
    id!: number;
    name!: string;
    phase!: string; 
    tasks!: TaskDto[]; 
    managerId!: number; 
  }
  