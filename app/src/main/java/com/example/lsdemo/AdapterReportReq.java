package com.example.lsdemo;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class AdapterReportReq extends RecyclerView.Adapter<AdapterReportReq.ViewHolder> {



    public List<ClassReport> reportList;
    public Context context;
   // public String colref;
    public CricketReportActivity cricketReportActivity;






    public AdapterReportReq(Context applicationContext, List<ClassReport> requestReportList) {
        this.context=applicationContext;
        this.reportList=requestReportList;
    }


    @NonNull
    @Override
    public AdapterReportReq.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.report_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterReportReq.ViewHolder holder, final int position) {

        holder.team1.setText(reportList.get(position).team1);
        holder.team2.setText(reportList.get(position).team2);
        final String docref=reportList.get(position).reporId;
        Toast.makeText(context,reportList.get(position).team2, Toast.LENGTH_SHORT).show();

        holder.downloadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdf(docref,position);
                Toast.makeText(context, reportList.get(position).t2Run, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView team1,team2;
        public ImageView downloadimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            team1=(TextView)itemView.findViewById(R.id.team1);
            team2=(TextView)itemView.findViewById(R.id.team2);
            downloadimg=(ImageView) itemView.findViewById(R.id.downloadReport);



        }
    }

    public void pdf(String docref, int position){

        Document mDoc=new Document();
        String filename="CricketReport";
        String filepath= Environment.getExternalStorageDirectory().getPath()  +"/"+ filename +".pdf";

        try {
            PdfWriter.getInstance(mDoc, new FileOutputStream(filepath));
            mDoc.setPageSize(PageSize.A4);
            mDoc.setMargins(70, 70, 70, 70);
            mDoc.setMarginMirroring(true);

            mDoc.open();


            com.itextpdf.text.Rectangle rect=new com.itextpdf.text.Rectangle(577,825,18,15);
            // Rectangle rect= new Rectangle(577,825,18,15);
            rect.enableBorderSide(1);
            rect.enableBorderSide(2);
            rect.enableBorderSide(4);
            rect.enableBorderSide(8);
            rect.setBorder(2);
            rect.setBorder(Rectangle.BOX);
            rect.setBorderColor(BaseColor.BLACK);
            rect.setBorderWidth(2);
            mDoc.add(rect);

            com.itextpdf.text.Font f1= new com.itextpdf.text.Font (com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 13,com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
            com.itextpdf.text.Paragraph p1=new com.itextpdf.text.Paragraph("Sardar Vallabhbhi Patel Education Society's",f1);
            p1.setAlignment(Element.ALIGN_CENTER);
            mDoc.add(p1);

            com.itextpdf.text.Font font = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 26, com.itextpdf.text.Font.UNDERLINE, BaseColor.BLACK);
            Paragraph title= new Paragraph("R. N. G. Patel Institute Of Technology",font);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(2.5f);
            mDoc.add(title);

            Paragraph p2=new Paragraph("At. Isroli-afwa,Bardoli-Navsari Road, Ta-Bardoli, Dist. Surat " +
                    "Pin 394620 \n E-mail: rngpit@gmil.com , Website: www.rngpit.ac.in , Ph. 92280 00867",f1);
            p2.setAlignment(Element.ALIGN_CENTER);
            p2.setSpacingAfter(30.5f);
            mDoc.add(p2);

            com.itextpdf.text.Font f2 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 20, com.itextpdf.text.Font.BOLDITALIC, BaseColor.BLACK);
            Paragraph title1=new Paragraph("SPORTS 2020",f2);
            Paragraph subtitle=new Paragraph("Cricket Report",f2);
            Paragraph vs=new Paragraph(reportList.get(position).team1+"   V/s  "+reportList.get(position).team2);
            vs.setAlignment(Element.ALIGN_CENTER);

            subtitle.setAlignment(Element.ALIGN_CENTER);
            title1.setAlignment(Element.ALIGN_CENTER);
            title1.setSpacingAfter(20.5f);
            mDoc.add(title1);
            mDoc.add(subtitle);



            com.itextpdf.text.Font f3= new com.itextpdf.text.Font (com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 18,com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
            Paragraph team1=new Paragraph(reportList.get(position).team1,f3);
            Paragraph team2=new Paragraph(reportList.get(position).team2,f3);
            team1.setSpacingAfter(3f);
            mDoc.add(vs);
            mDoc.add(team1);


            com.itextpdf.text.Font t1detail= new com.itextpdf.text.Font (com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 14,com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
                Paragraph t1run=new Paragraph("RUN:- " +reportList.get(position).t1Run ,t1detail );
                Paragraph t1Over=new Paragraph("Over " +reportList.get(position).t1Over,t1detail  );
                Paragraph t1wicket=new Paragraph("Wicket " +reportList.get(position).t1Wicket  ,t1detail);
                t1run.setSpacingAfter(2f);
                t1Over.setSpacingAfter(2f);
                t1wicket.setSpacingAfter(5f);
                mDoc.add(t1run);
                mDoc.add(t1Over);
                mDoc.add(t1wicket);

            mDoc.add(team2);
            Paragraph t2run=new Paragraph("RUN:- " +reportList.get(position).t2Run ,t1detail );
            Paragraph t2Over=new Paragraph("Over " +reportList.get(position).t2Over,t1detail  );
            Paragraph t2wicket=new Paragraph("Wicket " +reportList.get(position).t2Wicket  ,t1detail);
            t2run.setSpacingAfter(2f);
            t2Over.setSpacingAfter(2f);
            t2wicket.setSpacingAfter(5f);
            mDoc.add(t2run);
            mDoc.add(t2Over);
            mDoc.add(t2wicket);


            mDoc.add(new Chunk("\n"));
            //mDoc.add(new LineSeparator(2f,100,BaseColor.DARK_GRAY,Element.ALIGN_CENTER,-1f));

            mDoc.close();

            Toast.makeText(context,filename+".pdf\nis saved to\n"+filepath,Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException | DocumentException e) {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}
